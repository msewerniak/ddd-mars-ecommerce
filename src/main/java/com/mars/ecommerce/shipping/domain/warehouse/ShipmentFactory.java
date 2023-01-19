package com.mars.ecommerce.shipping.domain.warehouse;

import com.mars.ecommerce.shipping.readmodel.OrderDto;
import com.mars.ecommerce.shipping.readmodel.OrderItemDto;
import org.springframework.stereotype.Component;

@Component
public class ShipmentFactory {

    public Shipment create(OrderDto order) {
        return Shipment.create(order.id(), order.orderItems().stream().map(this::mapTo).toList());
    }

    private ShipmentItem mapTo(OrderItemDto i) {
        return new ShipmentItem(i.orderId(), i.productId(), i.productName(), i.productCategory(),
                i.productDescription(), i.quantity());
    }

}
