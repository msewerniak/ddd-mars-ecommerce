package com.mars.ecommerce.sales.domain.order;

import com.mars.ecommerce.sales.domain.cart.CartId;
import com.mars.ecommerce.sales.domain.client.ClientSnapshot;
import com.mars.ecommerce.sales.domain.delivery.DeliveryData;
import com.mars.ecommerce.sales.domain.offer.Offer;
import com.mars.ecommerce.sales.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderFactory {

    private final ProductRepository productRepository;

    public Order createOrder(CartId cartId, Offer offer, DeliveryData deliveryData, ClientSnapshot clientData) {

        // TODO: implement me
        return null;
    }
}
