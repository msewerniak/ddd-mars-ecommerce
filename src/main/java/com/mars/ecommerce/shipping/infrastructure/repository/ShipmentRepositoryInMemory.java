package com.mars.ecommerce.shipping.infrastructure.repository;

import com.mars.ecommerce.shipping.domain.warehouse.Shipment;
import com.mars.ecommerce.shipping.domain.warehouse.ShipmentRepository;
import com.mars.ecommerce.support.ddd.DddAnnotations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@DddAnnotations.DomainRepositoryImpl
@Component
public class ShipmentRepositoryInMemory implements ShipmentRepository {

    private final Map<UUID, Shipment> internal = new HashMap<>();

    @Override
    public void create(Shipment shipment) {

        if (internal.containsKey(shipment.shipmentId())) {
            throw new IllegalStateException("This shipment was already added");
        }

        internal.put(shipment.shipmentId(), shipment);
    }
}
