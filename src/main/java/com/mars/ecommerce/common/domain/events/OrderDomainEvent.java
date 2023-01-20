package com.mars.ecommerce.common.domain.events;

import com.mars.ecommerce.common.DomainEvent;

import java.util.UUID;

public interface OrderDomainEvent extends DomainEvent {

    UUID orderId();
}
