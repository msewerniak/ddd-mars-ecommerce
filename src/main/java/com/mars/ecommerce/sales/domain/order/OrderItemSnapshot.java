package com.mars.ecommerce.sales.domain.order;

import com.mars.ecommerce.support.ddd.DddAnnotations;

@DddAnnotations.DataSnapshot
public record OrderItemSnapshot(String orderId, long productId, String productName, String productCategory,
                                String productDescription, int quantity, long totalPrice, String totalPriceCurrency) {
}
