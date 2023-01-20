package com.mars.ecommerce.sales.domain.order;

import com.mars.ecommerce.support.ddd.DddAnnotations;

import java.util.UUID;

@DddAnnotations.DomainRepository
public interface OrderRepository {

    OrderSnapshot find(UUID orderId);

    void save(OrderSnapshot order);
}
