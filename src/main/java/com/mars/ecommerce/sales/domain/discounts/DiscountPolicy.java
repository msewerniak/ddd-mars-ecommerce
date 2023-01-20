package com.mars.ecommerce.sales.domain.discounts;

import com.mars.ecommerce.sales.domain.offer.Offer;
import com.mars.ecommerce.sales.domain.prices.Price;

public interface DiscountPolicy {

    Price apply(Offer price);

    String description();
}
