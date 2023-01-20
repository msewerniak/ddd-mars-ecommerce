package com.mars.ecommerce.sales.domain.client;

import com.mars.ecommerce.support.ddd.DddAnnotations;

import java.util.UUID;

@DddAnnotations.DataSnapshot
public record ClientSnapshot(UUID id, String name) {
}
