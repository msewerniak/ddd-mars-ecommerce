package com.mars.ecommerce.common.domain.events;

import com.mars.ecommerce.common.DomainEvent;
import com.mars.ecommerce.support.ddd.DddAnnotations;

import java.time.Instant;
import java.util.UUID;

@DddAnnotations.DomainEvent
public interface OrderDomainEvent extends DomainEvent {

    UUID orderId();

    record OrderSubmittedEvent(UUID eventId, Instant when, UUID orderId) implements OrderDomainEvent {

        public OrderSubmittedEvent(UUID orderId) {
            this(UUID.randomUUID(), Instant.now(), orderId);
        }
    }
}
