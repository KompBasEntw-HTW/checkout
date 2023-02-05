package de.extremecoffee;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import de.extremecoffee.checkout.Address;
import de.extremecoffee.checkout.CoffeeOrder;
import de.extremecoffee.dtos.PlaceOrderDto;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@SecurityRequirement(name = "Bearer Authentication")
@Authenticated
public class CheckoutController {
  @Inject
  CheckoutService checkoutService;
  @Inject
  SecurityIdentity identity;

  @GET
  @Path("/orders")
  @APIResponse(responseCode = "200", description = "Returns list of orders of logged in user", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = CoffeeOrder.class)))
  @APIResponse(responseCode = "404", description = "No orders found")
  public Response getOrders() {
    List<CoffeeOrder> userOrders = checkoutService.getOrders(identity.getPrincipal().getName());
    if (userOrders == null) {
      return Response.status(404).build();
    }
    return Response.ok().entity(userOrders).build();
  }

  @POST
  @Path("placeOrder")
  public Response placeOrder(PlaceOrderDto placeOrderDto) {
    var order = checkoutService.placeOrder(placeOrderDto, identity.getPrincipal().getName());
    return Response.ok(order).build();
  }

  @POST
  @Path("addAddress")
  @APIResponse()
  public Response addAddress(Address address) {
    var addressId = checkoutService.addAddress(address, identity.getPrincipal().getName());
    return Response.ok(addressId).build();
  }

  @GET
  @Path("getAddresses")
  @APIResponse(responseCode = "200", description = "Returns List of all saved user addresses")
  public Response getAddresses() {
    var addresses = checkoutService.getAddresses(identity.getPrincipal().getName());
    return Response.ok(addresses).build();
  }
}
