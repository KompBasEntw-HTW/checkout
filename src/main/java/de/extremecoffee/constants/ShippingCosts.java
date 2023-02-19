package de.extremecoffee.constants;

public enum ShippingCosts {
  STANDARD_SHIPPING_COST(5),
  EXPRESS_SHIPPING_COST(10),
  DISCOUNTED_STANDARD_SHIPPING_COST(0),
  DISCOUNTED_EXPRESS_SHIPPING_COST(5);

  public final int costs;
  ShippingCosts(int costs) {
    this.costs = costs;
  }
}
