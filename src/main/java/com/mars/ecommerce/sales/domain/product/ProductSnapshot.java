package com.mars.ecommerce.sales.domain.product;

import com.mars.ecommerce.support.ddd.DddAnnotations;

@DddAnnotations.DataSnapshot
public record ProductSnapshot(Long id, String name, String category, String description) {

}
