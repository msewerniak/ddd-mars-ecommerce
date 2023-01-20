package com.mars.ecommerce.sales.domain.discounts;

import com.mars.ecommerce.sales.domain.offer.Offer;
import com.mars.ecommerce.sales.domain.prices.Price;
import com.mars.ecommerce.support.ddd.DddAnnotations;

@DddAnnotations.DomainPolicy
public interface DiscountPolicy {

    Price apply(Offer price);

    String description();
}
