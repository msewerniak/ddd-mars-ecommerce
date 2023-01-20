package com.mars.ecommerce.sales.domain.order;

import com.mars.ecommerce.common.DomainEventPublisher;
import com.mars.ecommerce.common.domain.events.OrderDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderDatabase {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher eventPublisher;

    public void create(Order order) {
        orderRepository.save(order.snapshot());
        eventPublisher.publish(new OrderDomainEvent.OrderSubmittedEvent(order.id().id()));
    }

    public Order find(OrderId orderId) {
        return Order.fromSnapshot(orderRepository.find(orderId.id()));
    }
}
