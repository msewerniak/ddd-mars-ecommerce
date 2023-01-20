package com.mars.ecommerce.sales.domain.order;

import com.mars.ecommerce.sales.domain.cart.CartId;
import com.mars.ecommerce.sales.domain.client.ClientSnapshot;
import com.mars.ecommerce.sales.domain.delivery.DeliveryData;
import com.mars.ecommerce.sales.domain.offer.Offer;
import com.mars.ecommerce.sales.domain.offer.OfferItem;
import com.mars.ecommerce.sales.domain.product.ProductRepository;
import com.mars.ecommerce.sales.domain.product.ProductSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderFactory {

    private final ProductRepository productRepository;

    public Order createOrder(CartId cartId, Offer offer, DeliveryData deliveryData, ClientSnapshot clientData) {

        List<OrderItem> orderItems = offer.offerItems().stream().map(this::toOrderItem).toList();

        return Order.create(new OrderId(cartId.id()), clientData, orderItems, deliveryData,
                offer.appliedDiscount().description(), Order.PaymentStatus.STARTED);
    }

    private OrderItem toOrderItem(OfferItem offerItem) {

        ProductSnapshot product = productRepository.find(offerItem.productId().id());

        if (product == null) {
            throw new IllegalArgumentException("Product must exist!");
        }

        return new OrderItem(product, offerItem.amount(), offerItem.price());
    }
}
