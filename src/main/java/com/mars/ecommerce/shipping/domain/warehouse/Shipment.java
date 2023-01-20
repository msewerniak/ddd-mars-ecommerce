package com.mars.ecommerce.shipping.domain.warehouse;

import com.mars.ecommerce.support.ddd.DddAnnotations;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DddAnnotations.AggregateRoot
@NoArgsConstructor
public class Shipment {

    private UUID shipmentId;
    private List<ShipmentItem> items;
    private Status status;
    private Instant shippedAt;
    private Instant deliveredAt;

    public UUID shipmentId() {
        return shipmentId;
    }

    private Shipment(UUID shipmentId, List<ShipmentItem> items) {
        this.shipmentId = shipmentId;
        this.items = items;
    }
    
    public static Shipment create(UUID orderId, List<ShipmentItem> items) {
        return new Shipment(orderId, new ArrayList<>(items));
    }

    public void ship() {
        if (status == Status.DELIVERED) {
            throw new IllegalStateException("shipment is already completed");
        }
        shippedAt = Instant.now();
        status = Status.SENT;
    }
    
    public void complete() {
        if (status == Status.SENT) {
            throw new IllegalStateException("Shipment hasn't been sent yet");
        }
        deliveredAt = Instant.now();
        status = Status.DELIVERED;
    }
    
    enum Status {
        WAITING,
        PREPARED,
        SENT,
        DELIVERED
    }
}

@AllArgsConstructor
class ShipmentItem {

    private UUID orderId;
    private long productId;
    private String productName;
    private String productCategory;
    private String productDescription;
    private int quantity;
}