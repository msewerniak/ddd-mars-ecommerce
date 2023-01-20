package com.mars.ecommerce.shipping.api.listeners;

import com.mars.ecommerce.shipping.domain.warehouse.Shipment;
import com.mars.ecommerce.shipping.domain.warehouse.ShipmentFactory;
import com.mars.ecommerce.shipping.domain.warehouse.ShipmentRepository;
import com.mars.ecommerce.shipping.readmodel.OrderDto;
import com.mars.ecommerce.shipping.readmodel.OrderFinder;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class OrderSubmittedForShippingListener {

    private final OrderFinder orderFinder;
    private final ShipmentFactory shipmentFactory;
    private final ShipmentRepository shipmentRepository;

    @EventListener
    @Async
    public void handle(OrderSubmittedEvent event) {
        OrderDto orderDetails = orderFinder.find(event.orderId());

        Shipment shipment = shipmentFactory.create(orderDetails);

        shipmentRepository.create(shipment);
    }
}
