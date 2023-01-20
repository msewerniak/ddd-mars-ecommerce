package com.mars.ecommerce.sales.domain.discounts;

import com.mars.ecommerce.sales.domain.offer.Offer;
import com.mars.ecommerce.sales.domain.prices.Price;
import com.mars.ecommerce.support.ddd.DddAnnotations;

@DddAnnotations.DomainPolicyImpl
public class Fixed10EuroDiscount implements DiscountPolicy {

    static final Price MINIMAL_DISCOUNT_PRICE = Price.euro(100L);
    static final Price DISCOUNT_VALUE = Price.euro(10L);

    public Price apply(Offer offer) {

        Price beforeDiscount = offer.totalPrice();

        if (!beforeDiscount.lowerThan(MINIMAL_DISCOUNT_PRICE)) {
            return beforeDiscount.subtract(DISCOUNT_VALUE);
        }

        return beforeDiscount;
    }

    @Override
    public String description() {
        return "10 euro discount for the order 100 euro or more";
    }
}
