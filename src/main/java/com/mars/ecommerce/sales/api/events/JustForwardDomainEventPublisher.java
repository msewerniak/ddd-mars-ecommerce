package com.mars.ecommerce.sales.api.events;

import com.mars.ecommerce.common.DomainEvent;
import com.mars.ecommerce.common.DomainEventPublisher;
import com.mars.ecommerce.support.ddd.DddAnnotations;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@DddAnnotations.EventPublisherImpl
@AllArgsConstructor
@Component
public class JustForwardDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent domainEvent) {
        applicationEventPublisher.publishEvent(domainEvent);
    }
}
