package com.mars.ecommerce.common;

public interface DomainEventPublisher {

    void publish(DomainEvent domainEvent);
}
