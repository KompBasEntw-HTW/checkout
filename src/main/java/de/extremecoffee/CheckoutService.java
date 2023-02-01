package de.extremecoffee;

import java.util.List;

import de.extremecoffee.checkout.Address;
import de.extremecoffee.checkout.Item;
import de.extremecoffee.checkout.Order;
import de.extremecoffee.checkout.OrderItem;
import de.extremecoffee.dtos.ItemDto;
import de.extremecoffee.dtos.PlaceOrderDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CheckoutService {
  List<Order> getOrders(String userName) {
    return Order.getUserOrders(userName);
  }

  @Transactional
  Long addAddress(Address address) {
    address.persist();
    return address.id;
  }

  @Transactional
  Order placeOrder(PlaceOrderDto placeOrderDto, String userName) {
    var order = new Order();
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
}
