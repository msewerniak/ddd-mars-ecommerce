package com.mars.ecommerce.sales.domain.discounts;

import org.springframework.stereotype.Component;

@Component
public class DiscountFactory {

    public DiscountPolicy create() {
        return new Fixed10EuroDiscount();
    }
}
