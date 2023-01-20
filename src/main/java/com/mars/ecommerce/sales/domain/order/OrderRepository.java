package com.mars.ecommerce.sales.domain.order;

import java.util.UUID;

public interface OrderRepository {

    OrderSnapshot find(UUID orderId);

    void save(OrderSnapshot order);
}
