package com.mars.ecommerce.sales.domain.client;

import java.util.UUID;

public record ClientId(UUID id) {

    public static ClientId generate() {
        return new ClientId(UUID.randomUUID());
    }
}
