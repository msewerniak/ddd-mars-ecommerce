package com.mars.ecommerce.common.domain.events;

import com.mars.ecommerce.common.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public interface OrderDomainEvent extends DomainEvent {

    UUID orderId();

    record OrderSubmittedEvent(UUID eventId, Instant when, UUID orderId) implements OrderDomainEvent {

        public OrderSubmittedEvent(UUID orderId) {
            this(UUID.randomUUID(), Instant.now(), orderId);
        }
    }
}
