package com.mars.ecommerce.sales.domain.client;

import com.mars.ecommerce.support.ddd.DddAnnotations;

import java.util.UUID;

@DddAnnotations.ValueObject
public record ClientId(UUID id) {

    public static ClientId generate() {
        return new ClientId(UUID.randomUUID());
    }
}
