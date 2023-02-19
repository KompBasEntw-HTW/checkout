package de.extremecoffee.checkout;

import de.extremecoffee.constants.ShippingCosts;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class ShippingMethod {
  public String id;

  public String title;

  public String turnaround;
  public int basePrice;

  public int reducedPrice;

  public ShippingMethod(String id, String title, String turnaround, ShippingCosts basePrice, ShippingCosts reducedPrice) {
    this.id = id;
    this.title = title;
    this.turnaround = turnaround;
    this.basePrice = basePrice.costs;
    this.reducedPrice = reducedPrice.costs;
  }
}
