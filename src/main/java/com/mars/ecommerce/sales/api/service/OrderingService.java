package com.mars.ecommerce.sales.api.service;

import com.mars.ecommerce.sales.domain.cart.Cart;
import com.mars.ecommerce.sales.domain.cart.CartId;
import com.mars.ecommerce.sales.domain.cart.CartItem;
import com.mars.ecommerce.sales.domain.cart.CartRepository;
import com.mars.ecommerce.sales.domain.cart.ExtraItemsPolicy;
import com.mars.ecommerce.sales.domain.client.Client;
import com.mars.ecommerce.sales.domain.client.ClientId;
import com.mars.ecommerce.sales.domain.delivery.DeliveryData;
import com.mars.ecommerce.sales.domain.discounts.DiscountFactory;
import com.mars.ecommerce.sales.domain.offer.Offer;
import com.mars.ecommerce.sales.domain.order.Order;
import com.mars.ecommerce.sales.domain.order.OrderDatabase;
import com.mars.ecommerce.sales.domain.order.OrderFactory;
import com.mars.ecommerce.sales.domain.order.OrderId;
import com.mars.ecommerce.sales.domain.prices.Pricing;
import com.mars.ecommerce.sales.domain.product.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class OrderingService {

    private final CartRepository cartRepository;
    private final ExtraItemsPolicy extraItemsPolicy;
    private final DiscountFactory discountFactory;
    private final Pricing pricing;
    private final OrderFactory orderFactory;
    private final OrderDatabase orderDatabase;

    public CartId createOrder() {
        Cart cart = Cart.create();
        cartRepository.save(cart);

        return cart.id();
    }

    public void addProductToCart(CartId cartId, ProductId productId) {

        // add order item
        CartItem itemToBeAdded = new CartItem(productId, pricing.productPrice(productId));

        Cart cart = cartRepository.find(cartId);
        cart.add(itemToBeAdded);

        // add free products
        Set<CartItem> freeProducts = extraItemsPolicy.findAllFor(itemToBeAdded.productId());
        freeProducts.forEach(cart::addFree);

        cartRepository.save(cart);
    }

    public Offer calculateOffer(CartId cartId) {

        Cart cart = cartRepository.find(cartId);

        return cart.calculateOffer(discountFactory.create());
    }

    public OrderId confirm(CartId cartId, Offer seenOffer, DeliveryData deliveryData) {

        Client client = clientFromSystemContext();

        Cart cart = cartRepository.find(cartId);

        Offer calculatedOffer = cart.calculateOffer(discountFactory.create());

        if (!calculatedOffer.sameAs(seenOffer)) {
            throw new OfferChangedException();
        }

        Order order = orderFactory.createOrder(cartId, calculatedOffer, deliveryData, client.snapshot());

        orderDatabase.create(order);

        return order.id();
    }

    private Client clientFromSystemContext() {
        return new Client(ClientId.generate(), "Joe Black"); //TODO: Spring security integration
    }

    public static class OfferChangedException extends RuntimeException {
    }
}
