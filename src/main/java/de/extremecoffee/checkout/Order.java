package de.extremecoffee.checkout;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Order extends PanacheEntityBase {
  @Id
  @GeneratedValue
  public Long id;

  @CreationTimestamp
  public ZonedDateTime orderDateTime;

  public String userName;

  @ManyToOne
  public Address address;

  @OneToMany(mappedBy = "order")
  public Set<OrderItem> orderItems = new HashSet<OrderItem>();

  public static List<Order> getUserOrders(String userName) {
    return list("userName", userName);
  }
}
