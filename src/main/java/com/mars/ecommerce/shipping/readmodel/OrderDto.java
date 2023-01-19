package com.mars.ecommerce.shipping.readmodel;

import java.util.List;
import java.util.UUID;

public record OrderDto(UUID id, List<OrderItemDto> orderItems, String deliveryAddress, String orderStatus) {
}

