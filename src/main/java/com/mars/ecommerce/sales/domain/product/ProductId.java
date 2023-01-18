package com.mars.ecommerce.sales.domain.product;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ProductId {

    private final long id;

    public ProductId(Long id) {
        if (id < 100000 || id >= 1000000) {
            throw new IllegalArgumentException("Product id must have 6 digits");
        }
        this.id = id;
    }

    public Long id() {
        return id;
    }
}
