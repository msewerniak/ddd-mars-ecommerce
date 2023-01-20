package com.mars.ecommerce.common;

import com.mars.ecommerce.support.ddd.DddAnnotations;

import java.time.Instant;
import java.util.UUID;

@DddAnnotations.DomainEvent
public interface DomainEvent {

    UUID eventId();

    Instant when();
}
