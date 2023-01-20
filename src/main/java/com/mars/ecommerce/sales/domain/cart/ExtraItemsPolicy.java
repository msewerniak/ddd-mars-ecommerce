package com.mars.ecommerce.sales.domain.cart;

import com.mars.ecommerce.sales.domain.product.ProductId;
import com.mars.ecommerce.support.ddd.DddAnnotations;

import java.util.Set;

@DddAnnotations.DomainPolicy
public interface ExtraItemsPolicy {

    Set<CartItem> findAllFor(ProductId addedItem);

    void add(ProductId forItem, ProductId freeItem);
}
