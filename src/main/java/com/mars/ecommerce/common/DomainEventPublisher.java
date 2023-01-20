package com.mars.ecommerce.common;

import com.mars.ecommerce.support.ddd.DddAnnotations;

@DddAnnotations.EventPublisher
public interface DomainEventPublisher {

    void publish(DomainEvent domainEvent);
}
