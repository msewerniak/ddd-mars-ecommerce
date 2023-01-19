package com.mars.ecommerce.shipping.readmodel;

import java.util.UUID;

public record OrderItemDto(UUID orderId, long productId, String productName, String productCategory,
                           String productDescription, int quantity) {
}
