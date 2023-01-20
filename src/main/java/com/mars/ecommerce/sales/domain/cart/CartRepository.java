package com.mars.ecommerce.sales.domain.cart;

import com.mars.ecommerce.support.ddd.DddAnnotations;

@DddAnnotations.DomainRepository
public interface CartRepository {

    Cart find(CartId cartId);

    void save(Cart cart);
}
