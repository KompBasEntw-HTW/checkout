package de.extremecoffee.checkout.entities;

import java.io.Serializable;

public class ItemId implements Serializable {
	public Long bagSizeId;

	public Long productId;

	@Override
	public boolean equals(Object o) {
		ItemId itemId = (ItemId) o;
		return itemId.bagSizeId == this.bagSizeId && itemId.productId == this.productId;
	}
}
