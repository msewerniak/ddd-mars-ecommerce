package com.mars.ecommerce.sales.domain.cart;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Cart {

    private final CartId id;

    public CartId id() {
        return id;
    }
}
