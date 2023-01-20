package com.mars.ecommerce.sales.domain.discounts;

import com.mars.ecommerce.support.ddd.DddAnnotations;
import org.springframework.stereotype.Component;

@DddAnnotations.DomainFactory
@Component
public class DiscountFactory {

    public DiscountPolicy create() {
        return new Fixed10EuroDiscount();
    }
}
