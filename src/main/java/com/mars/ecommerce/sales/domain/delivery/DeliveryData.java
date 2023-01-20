package com.mars.ecommerce.sales.domain.delivery;

import com.mars.ecommerce.support.ddd.DddAnnotations;

@DddAnnotations.DataSnapshot
public record DeliveryData(String address) {
}
