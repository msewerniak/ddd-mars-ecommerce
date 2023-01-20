package com.mars.ecommerce.sales.domain.cart;

import com.mars.ecommerce.support.ddd.DddAnnotations;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@DddAnnotations.DomainService
@RequiredArgsConstructor
class CartService {

    private final CartRepository cartRepository;
    private final ExtraItemsPolicy extraItemsPolicy;

    void addItem(CartId cartId, CartItem addedItem) {
        Cart cart = cartRepository.find(cartId);
        cart.add(addedItem);
        Set<CartItem> freeItems = extraItemsPolicy.findAllFor(addedItem.productId());
        freeItems.forEach(cart::addFree);
        cartRepository.save(cart);
    }

    void removeItem(CartId cartId, CartItem removedItem) {
        Cart cart = cartRepository.find(cartId);
        cart.remove(removedItem);
        Set<CartItem> freeItems = extraItemsPolicy.findAllFor(removedItem.productId());
        freeItems.forEach(cart::removeFreeItem);
        cartRepository.save(cart);
    }

    void intentionallyRemoveFreeItem(CartId cartId, CartItem removedItem) {
        Cart cart = cartRepository.find(cartId);
        cart.removeFreeItemIntentionally(removedItem);
        cartRepository.save(cart);
    }

    void addFreeItemBack(CartId cartId, CartItem addedItem) {
        Cart cart = cartRepository.find(cartId);
        cart.addFreeItemBack(addedItem);
        cartRepository.save(cart);
    }

}