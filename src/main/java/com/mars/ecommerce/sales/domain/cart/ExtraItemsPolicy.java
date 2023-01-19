package com.mars.ecommerce.sales.domain.cart;

import com.mars.ecommerce.sales.domain.product.ProductId;

import java.util.Set;

public interface ExtraItemsPolicy {

    Set<CartItem> findAllFor(ProductId addedItem);

    void add(ProductId forItem, ProductId freeItem);
}
