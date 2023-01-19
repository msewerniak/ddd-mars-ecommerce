package com.mars.ecommerce.sales.domain.cart;

public interface CartRepository {

    Cart find(CartId cartId);

    void save(Cart cart);
}
