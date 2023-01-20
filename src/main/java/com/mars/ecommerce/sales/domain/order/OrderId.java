package com.mars.ecommerce.sales.domain.order;

import com.mars.ecommerce.support.ddd.DddAnnotations;

import java.util.UUID;

@DddAnnotations.ValueObject
public record OrderId(UUID id) {
}
