package de.extremecoffee.checkout;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItem extends PanacheEntityBase {

  @Id
  @GeneratedValue
  @Schema(readOnly = true)
  public Long id;

  @ManyToOne
  @JoinColumns({ @JoinColumn(name = "bagsizeid"), @JoinColumn(name = "productid") })
  public Item item;

  @ManyToOne
  @JsonbTransient
  @JoinColumn(name = "order_id")
  public Order order;

  public Integer quantity;

}
