package com.mars.ecommerce.sales.domain.prices;

import com.mars.ecommerce.sales.domain.offer.Amount;

public record Price(Long value, String currency) {

    public static final Price ZERO = euro(0L);

    public static Price euro(Long value) {
        return new Price(value, "EUR");
    }

    public static Price min(Price first, Price second) {
        validate(first, second);

        return first.value < second.value ? first : second;
    }

    private static int compare(Price first, Price second) {
        validate(first, second);

        return first.value.compareTo(second.value);
    }

    private static void validate(Price first, Price second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("The price is null");
        }

        if (!first.currency.equals(second.currency)) {
            throw new IllegalArgumentException("Prices are in different currency");
        }
    }

    public boolean lowerThan(Price price) {
        return compare(this, price) < 0;
    }

    public Price multipleBy(Amount amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        Integer times = amount.value();
        return new Price(times * value, currency);
    }

    public Price subtract(Price val) {
        validate(this, val);
        return new Price(value - val.value, currency);
    }

    public Price add(Price val) {
        validate(this, val);
        return new Price(value + val.value, currency);
    }
}
