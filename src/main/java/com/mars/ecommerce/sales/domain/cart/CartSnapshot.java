package com.mars.ecommerce.sales.domain.cart;

import java.util.List;
import java.util.UUID;

public record CartSnapshot(UUID id, List<CartItemSnapshot> items) {

    public record CartItemSnapshot(UUID cartId, long productId, long price, String currency, CartItemType type) {

        public enum CartItemType {
            ITEM,
            FREE_ITEM,
            INTENTIONALLY_REMOVED_FREE_ITEM
        }
    }
}
