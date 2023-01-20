package com.mars.ecommerce.sales.domain.order;

public record OrderItemSnapshot(String orderId, long productId, String productName, String productCategory,
                                String productDescription, int quantity, long totalPrice, String totalPriceCurrency) {
}
