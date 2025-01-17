package de.extremecoffee.checkout.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.util.Objects;

@Entity
@IdClass(ItemId.class)
public class Item extends PanacheEntityBase {
	@Id
	public Long bagSizeId;

	@Id
	public Long productId;

}
