package de.extremecoffee.constants;

import de.extremecoffee.checkout.ShippingMethod;

public enum ShippingMethods {
  STANDARD_SHIPPING(new ShippingMethod("standard", "Standard", "4-10 business days", ShippingCosts.STANDARD_SHIPPING_COST, ShippingCosts.DISCOUNTED_STANDARD_SHIPPING_COST)),
  EXPRESS_SHIPPING(new ShippingMethod("express", "Express", "2-5 business days", ShippingCosts.EXPRESS_SHIPPING_COST, ShippingCosts.DISCOUNTED_EXPRESS_SHIPPING_COST));
  ShippingMethod shippingMethod;
  ShippingMethods(ShippingMethod shippingMethod){
    this.shippingMethod = shippingMethod;
  };
}
