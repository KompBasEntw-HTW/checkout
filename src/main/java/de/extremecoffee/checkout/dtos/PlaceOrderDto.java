package de.extremecoffee.checkout.dtos;

import de.extremecoffee.checkout.enums.ShippingMethods;

import java.util.List;
import java.util.UUID;

public class PlaceOrderDto {
  public List<ItemDto> items;

  public UUID addressId;

  public String userEmail;

  public ShippingMethods shippingMethod;
}
