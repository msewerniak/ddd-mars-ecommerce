package com.mars.ecommerce.sales.infrastructure.repository.hashmap;

import com.mars.ecommerce.sales.domain.cart.CartItem;
import com.mars.ecommerce.sales.domain.cart.ExtraItemsPolicy;
import com.mars.ecommerce.sales.domain.product.ProductId;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExtraItemsPolicyInMemory implements ExtraItemsPolicy {

    private final Set<ExtraItem> extraItems = new HashSet<>();

    @Override
    public Set<CartItem> findAllFor(ProductId addedItem) {
        return extraItems.stream()
                .filter(extraItem -> extraItem.isFreeFor(addedItem))
                .map(ExtraItem::freeItem)
                .map(CartItem::free)
                .collect(Collectors.toSet());
    }

    @Override
    public void add(ProductId forItem, ProductId freeItem) {
        extraItems.add(new ExtraItem(forItem, freeItem));
    }

    private record ExtraItem(ProductId forItem, ProductId freeItem) {

        boolean isFreeFor(ProductId item) {
            return forItem.equals(item);
        }
    }
}