package de.extremecoffee.checkout;

import de.extremecoffee.constants.ShippingMethods;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class CoffeeOrder extends PanacheEntityBase {
  @Id @GeneratedValue public Long id;

  @CreationTimestamp public ZonedDateTime orderDateTime;

  public String userName;

  @ManyToOne public Address address;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "coffeeOrder")
  public Set<OrderItem> orderItems = new HashSet<OrderItem>();

  public static List<CoffeeOrder> getUserOrders(String userName) {
    return list("userName", userName);
  }
  public Boolean canceled = false;

  public Double subTotal;
  public Boolean valid = false;

  @Enumerated
  public ShippingMethods shippingMethod;
}
