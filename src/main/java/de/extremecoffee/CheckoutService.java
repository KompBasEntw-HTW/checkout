package de.extremecoffee;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import de.extremecoffee.checkout.Address;
import de.extremecoffee.checkout.Item;
import de.extremecoffee.checkout.CoffeeOrder;
import de.extremecoffee.checkout.OrderItem;
import de.extremecoffee.dtos.ItemDto;
import de.extremecoffee.dtos.ItemToValidateDto;
import de.extremecoffee.dtos.OrderValidationRequestDto;
import de.extremecoffee.dtos.PlaceOrderDto;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CheckoutService {

  @Channel("order-validation-request")
  Emitter<OrderValidationRequestDto> requestOrderValidationEmitter;

  List<CoffeeOrder> getOrders(String userName) {
    return CoffeeOrder.getUserOrders(userName);
  }

  @Transactional
  Long addAddress(Address address, String userName) {
    address.userName = userName;
    address.persist();
    return address.id;
  }

  @Transactional
  CoffeeOrder placeOrder(PlaceOrderDto placeOrderDto, String userName) {
    var order = new CoffeeOrder();
    if (!verifyItems(placeOrderDto.items)) {
      return null;
    }

    var itemsToValidate = new ArrayList<ItemToValidateDto>();
    for (var itemDto : placeOrderDto.items) {
      var itemToValidate = new ItemToValidateDto(itemDto.itemId.bagSizeId, itemDto.itemId.productId, itemDto.quantity);
      itemsToValidate.add(itemToValidate);
    }
    var orderValidationRequest = new OrderValidationRequestDto(itemsToValidate, 443L);

    Log.info("Requesting order validation:" + orderValidationRequest);
    requestOrderValidationEmitter.send(orderValidationRequest);

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
      orderItem.persist();
      order.orderItems.add(orderItem);
    }

    order.address = Address.findById(placeOrderDto.addressId);
    order.userName = userName;
    order.persist();
    return order;
  }

  private boolean verifyItems(List<ItemDto> items) {
    if (!items.isEmpty()) {
      return true;
    }
    return false;
  }

  List<Address> getAddresses(String userName) {
    return Address.getUserAddresses(userName);
  }

  // TODO
  private Double calculateSubTotal() {
    return 0d;
  }
}
