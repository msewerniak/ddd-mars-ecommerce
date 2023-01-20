package com.mars.ecommerce.shipping.domain.warehouse;

import com.mars.ecommerce.support.ddd.DddAnnotations;

@DddAnnotations.DomainRepository
public interface ShipmentRepository {

    void create(Shipment shipment);
}
