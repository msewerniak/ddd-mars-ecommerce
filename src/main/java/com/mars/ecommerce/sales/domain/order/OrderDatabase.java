package com.mars.ecommerce.sales.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderDatabase {

    private final OrderRepository orderRepository;

    public void create(Order order) {
        orderRepository.save(order.snapshot());
    }

    public Order find(OrderId orderId) {
        return Order.fromSnapshot(orderRepository.find(orderId.id()));
    }
}
