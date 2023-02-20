package de.extremecoffee;

import de.extremecoffee.checkout.*;
import de.extremecoffee.checkout.enums.ShippingMethods;
import de.extremecoffee.dtos.ItemDto;
import de.extremecoffee.dtos.ItemToValidateDto;
import de.extremecoffee.dtos.OrderValidationRequestDto;
import de.extremecoffee.dtos.PlaceOrderDto;
import io.quarkus.logging.Log;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class CheckoutService {

  @Channel("order-validation-request")
  Emitter<OrderValidationRequestDto> requestOrderValidationEmitter;

  List<CoffeeOrder> getOrders(String userName) {
    return CoffeeOrder.getUserOrders(userName);
  }

  boolean orderIsValid(Long orderId) throws NotFoundException {
    CoffeeOrder coffeeOrder = CoffeeOrder.findById(orderId);
    if (coffeeOrder == null) {
      throw new NotFoundException("Order to validate not found");
    }
    return coffeeOrder.valid;
  }

  @Transactional
  UUID addAddress(Address address) {
    address.persist();
    return address.id;
  }

  @Transactional
  CoffeeOrder placeOrder(PlaceOrderDto placeOrderDto) {
    var order = new CoffeeOrder();
    if (!verifyItems(placeOrderDto.items)) {
      return null;
    }

    for (var itemDto : placeOrderDto.items) {
      Item item = Item.findById(itemDto.itemId);
      if (item == null) {
        item = new Item();
        item.bagSizeId = itemDto.itemId.bagSizeId;
        item.productId = itemDto.itemId.productId;
        item.persist();
      }

      OrderItem orderItem = new OrderItem();
      orderItem.item = item;
      orderItem.quantity = itemDto.quantity;
      orderItem.coffeeOrder = order;
      orderItem.persist();
      order.orderItems.add(orderItem);
    }

    order.address = Address.findById(placeOrderDto.addressId);
    order.userName = placeOrderDto.userEmail;

    if (placeOrderDto.shippingMethod.equals(ShippingMethods.EXPRESS_SHIPPING)) {
      order.shippingMethod = ShippingMethod.findById("express");
    } else if (placeOrderDto.shippingMethod.equals(ShippingMethods.STANDARD_SHIPPING)) {
      order.shippingMethod = ShippingMethod.findById("standard");
    }
    if (!order.userName.equals(order.address.userName)) {
      throw new UnauthorizedException();
    }
    order.persist();

    var itemsToValidate = new ArrayList<ItemToValidateDto>();
    for (var itemDto : placeOrderDto.items) {
      var itemToValidate = new ItemToValidateDto(
              itemDto.itemId.bagSizeId, itemDto.itemId.productId, itemDto.quantity);
      itemsToValidate.add(itemToValidate);
    }
    var orderValidationRequest = new OrderValidationRequestDto(
            itemsToValidate.toArray(new ItemToValidateDto[0]), order.id);

    Log.info("Requesting order validation:" + orderValidationRequest);
    requestOrderValidationEmitter.send(orderValidationRequest);

    return order;
  }

  private boolean verifyItems(List<ItemDto> items) {
    return !items.isEmpty();
  }

  List<Address> getAddresses(String userName) {
    return Address.getUserAddresses(userName);
  }

  @Transactional
  public CoffeeOrder cancelOrder(Long orderId, String userName)
          throws UnauthorizedException, NotFoundException {
    CoffeeOrder order = CoffeeOrder.findById(orderId);
    if (order == null) {
      throw new NotFoundException();
    }
    if (!Objects.equals(userName, order.userName)) {
      throw new UnauthorizedException();
    }
    order.canceled = true;
    return order;
  }

  // TODO
  private Double calculateSubTotal() {
    return 0d;
  }
}
