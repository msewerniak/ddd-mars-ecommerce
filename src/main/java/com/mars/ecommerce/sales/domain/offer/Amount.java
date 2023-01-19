package com.mars.ecommerce.sales.domain.offer;

public record Amount(int value) {

    public static final Amount ZERO = new Amount(0);

    public Amount {
        if (value < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    public Amount increase() {
        return new Amount(value + 1);
    }
}
