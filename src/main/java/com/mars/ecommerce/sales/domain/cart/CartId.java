package com.mars.ecommerce.sales.domain.cart;

import com.mars.ecommerce.support.ddd.DddAnnotations;

import java.util.UUID;

@DddAnnotations.ValueObject
public record CartId(UUID id) {
    public static CartId generate() {
        return new CartId(UUID.randomUUID());
    }
}
