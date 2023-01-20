package com.mars.ecommerce.sales.api.events;

import com.mars.ecommerce.common.DomainEvent;
import com.mars.ecommerce.common.DomainEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JustForwardDomainEventPublisher implements DomainEventPublisher {

    @Override
    public void publish(DomainEvent domainEvent) {
        //TODO: implement
    }
}
