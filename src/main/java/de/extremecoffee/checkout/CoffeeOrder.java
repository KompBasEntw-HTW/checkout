package de.extremecoffee.checkout;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

  @OneToMany(mappedBy = "order")
  public Set<OrderItem> orderItems = new HashSet<OrderItem>();

  public static List<CoffeeOrder> getUserOrders(String userName) {
    return list("userName", userName);
  }
  public Boolean canceled = false;

  public Double subTotal;
  public Boolean valid = false;
}
