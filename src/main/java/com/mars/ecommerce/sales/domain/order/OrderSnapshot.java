package com.mars.ecommerce.sales.domain.order;

import java.util.List;
import java.util.UUID;

public record OrderSnapshot(UUID id, UUID clientId, String clientName, List<OrderItemSnapshot> orderItems,
                            String deliveryAddress, String discountDescription, String paymentStatus,
                            String orderStatus) {
}