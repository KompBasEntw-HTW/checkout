package de.extremecoffee;

import de.extremecoffee.checkout.Address;
import de.extremecoffee.checkout.CoffeeOrder;
import de.extremecoffee.dtos.PlaceOrderDto;
import io.quarkus.security.Authenticated;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.jboss.logging.annotations.Param;

@Path("/")
@SecurityRequirement(name = "Bearer Authentication")
public class CheckoutController {
  @Inject CheckoutService checkoutService;
  @Inject SecurityIdentity identity;

  @GET
  @Path("/orders")
  @Authenticated
  @APIResponse(
      responseCode = "200",
      description = "Returns list of orders of logged in user",
      content = @Content(mediaType = MediaType.APPLICATION_JSON,
                         schema = @Schema(type = SchemaType.ARRAY,
                                          implementation = CoffeeOrder.class)))
  @APIResponse(responseCode = "404", description = "No orders found")
  public Response
  getOrders() {
    List<CoffeeOrder> userOrders =
        checkoutService.getOrders(identity.getPrincipal().getName());
    if (userOrders == null) {
      return Response.status(404).build();
    }
    return Response.ok().entity(userOrders).build();
  }

  @POST
  @Path("/orders/place")
  @APIResponse(
      responseCode = "200", description = "Places order.",
      content = @Content(mediaType = MediaType.APPLICATION_JSON,
                         schema = @Schema(implementation = CoffeeOrder.class)))
  @APIResponse(responseCode = "400", description = "Order malformed.")
  public Response
  placeOrder(PlaceOrderDto placeOrderDto) {
    if (!identity.isAnonymous()) {
      placeOrderDto.userEmail = identity.getPrincipal().getName();
    };
    if (placeOrderDto.userEmail.isEmpty()) {
      return Response.status(400).build();
    }
    CoffeeOrder order;
    try {
      order = checkoutService.placeOrder(placeOrderDto);
    } catch (UnauthorizedException e) {
      return Response.status(403).build();
    }
    return Response.ok(order).build();
  }

  @POST
  @Path("/addresses/add")
  @APIResponse(responseCode = "200", description = "Creates address.")
  @APIResponse(responseCode = "404", description = "Address malformed.")
  public Response addAddress(Address address) {
    if (!identity.isAnonymous()) {
      address.userName = identity.getPrincipal().getName();
    }
    var addressId = checkoutService.addAddress(address);
    return Response.ok(addressId).build();
  }

  @GET
  @Path("/addresses")
  @Authenticated
  @APIResponse(
      responseCode = "200",
      content = @Content(mediaType = MediaType.APPLICATION_JSON,
                         schema = @Schema(type = SchemaType.ARRAY,
                                          implementation = Address.class)))
  public Response
  getAddresses() {
    var addresses =
        checkoutService.getAddresses(identity.getPrincipal().getName());
    return Response.ok(addresses).build();
  }

  @POST
  @Path("/orders/{orderId}/cancel")
  @APIResponse(
      responseCode = "200",
      content = @Content(mediaType = MediaType.APPLICATION_JSON,
                         schema = @Schema(implementation = CoffeeOrder.class)),
      description =
          "Cancels order so it doesnt get processed. Returns canceled order.")
  @APIResponse(
      responseCode = "403",
      description = "User is only permitted to cancel their own orders.")
  @APIResponse(responseCode = "404", description = "Order could not be found.")
  public Response
  cancelOrder(@Param Long orderId) {
    String userName = null;
    if (!identity.getPrincipal().getName().isEmpty()) {
      userName = identity.getPrincipal().getName();
    }
    try {
      var order = checkoutService.cancelOrder(orderId, userName);
      if (order == null) {
        return Response.status(404).build();
      }
      return Response.ok(order).build();
    } catch (UnauthorizedException unauthorizedException) {
      return Response.status(403).build();
    } catch (NotFoundException notFoundException) {
      return Response.status(404).build();
    }
  }
}
