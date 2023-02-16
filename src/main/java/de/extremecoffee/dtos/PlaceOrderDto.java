package de.extremecoffee.dtos;

import java.util.List;
import java.util.UUID;

public class PlaceOrderDto {
  public List<ItemDto> items;

  public UUID addressId;

  public String userEmail;
}
