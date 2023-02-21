package de.extremecoffee.checkout.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ShippingMethod extends PanacheEntityBase {

  @Id
  public String id;
  public String title;
  public String turnaround;
  public Integer basePrice;
  public Integer reducedPrice;
}