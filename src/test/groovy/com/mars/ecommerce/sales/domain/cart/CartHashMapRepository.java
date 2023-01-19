package com.mars.ecommerce.sales.domain.cart;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CartHashMapRepository implements CartRepository {

    private final Map<UUID, Cart> views = new HashMap<>();

    @Override
    public Cart find(CartId cartId) {
        return views.get(cartId.id());
    }

    @Override
    public void save(Cart cart) {
        views.put(cart.id().id(), cart);
    }
}
