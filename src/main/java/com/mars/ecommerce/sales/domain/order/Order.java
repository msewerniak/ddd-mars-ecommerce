package com.mars.ecommerce.sales.domain.order;

import com.mars.ecommerce.sales.domain.client.ClientSnapshot;
import com.mars.ecommerce.sales.domain.delivery.DeliveryData;
import com.mars.ecommerce.sales.domain.offer.Amount;
import com.mars.ecommerce.sales.domain.prices.Price;
import com.mars.ecommerce.sales.domain.product.ProductSnapshot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {

    private final OrderId orderId;
    private final ClientSnapshot client;
    private final List<OrderItem> orderItems;
    private final DeliveryData deliveryData;
    private final String discountDescription;
    private final PaymentStatus paymentStatus;
    private final OrderStatus status;

    public static Order fromSnapshot(OrderSnapshot s) {

        List<OrderItem> orderItems = s.orderItems().stream().map(OrderItem::fromSnapshot).toList();

        return new Order(new OrderId(s.id()), new ClientSnapshot(s.clientId(), s.clientName()), orderItems,
                new DeliveryData(s.deliveryAddress()), s.discountDescription(),
                PaymentStatus.valueOf(s.paymentStatus()), OrderStatus.valueOf(s.orderStatus()));
    }

    public static Order create(OrderId orderId, ClientSnapshot clientData, List<OrderItem> orderItems,
            DeliveryData deliveryData, String description, PaymentStatus paymentStatus) {

        return new Order(orderId, clientData, orderItems, deliveryData, description, paymentStatus, OrderStatus.CONFIRMED);
    }

    public OrderSnapshot snapshot() {
        return new OrderSnapshot(orderId.id(), client.id(), client.name(),
                orderItems.stream().map(i -> i.snapshot(orderId)).toList(), deliveryData.address(), discountDescription,
                paymentStatus.name(), status.name());
    }

    public OrderId id() {
        return orderId;
    }

    public OrderStatus status() {
        return status;
    }

    public enum OrderStatus {
        CONFIRMED,
        WAREHOUSING_STARTED,
        SHIPPING_STARTED,
        DELIVERY_CONFIRMED
    }

    public enum PaymentStatus {
        STARTED,
        PAID,
        FAILED
    }
}

@AllArgsConstructor
class OrderItem {

    ProductSnapshot productSnapshot;
    Amount quantity;
    Price totalPrice;

    public static OrderItem fromSnapshot(OrderItemSnapshot snapshot) {
        return new OrderItem(
                new ProductSnapshot(snapshot.productId(), snapshot.productName(), snapshot.productCategory(),
                        snapshot.productDescription()), new Amount(snapshot.quantity()),
                new Price(snapshot.totalPrice(), snapshot.totalPriceCurrency()));
    }

    OrderItemSnapshot snapshot(OrderId orderId) {
        return new OrderItemSnapshot(orderId.id().toString(), productSnapshot.id(), productSnapshot.name(),
                productSnapshot.category(),
                productSnapshot.description(), quantity.value(), totalPrice.value(), totalPrice.currency());
    }
}
