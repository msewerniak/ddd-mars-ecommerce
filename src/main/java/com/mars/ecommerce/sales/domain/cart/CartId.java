package com.mars.ecommerce.sales.domain.cart;

import java.util.UUID;

public record CartId(UUID id) {
    public static CartId generate() {
        return new CartId(UUID.randomUUID());
    }
}
