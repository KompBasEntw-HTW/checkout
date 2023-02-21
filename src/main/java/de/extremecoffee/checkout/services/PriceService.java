package de.extremecoffee.checkout.services;

import de.extremecoffee.checkout.constants.Constants;
import de.extremecoffee.checkout.entities.ShippingMethod;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PriceService {
  public Double calculateTax(Double subTotal){
    if(subTotal < 0){
      throw  new IllegalArgumentException();
    }
    return subTotal * Constants.TAX_RATE;
  }

  public Integer getShippingCosts(Double subTotal, ShippingMethod shippingMethod){
    Integer shippingCosts = shippingMethod.basePrice;
    if(subTotal > Constants.ORDER_DISCOUND_THRESHHOLD){
      shippingCosts = shippingMethod.reducedPrice;
    }
    return shippingCosts;
  }
}
