package com.mars.ecommerce.sales.domain.cart;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Cart {

    private final CartId id;
    
    private final List<CartItem> items = new ArrayList<>();
    private final List<CartItem> freeItems = new ArrayList<>();
    private final List<CartItem> intentionallyRemovedFreeItems = new ArrayList<>();

    public CartId id() {
        return id;
    }

    public void add(CartItem item) {
        items.add(item);
    }

    public void addFree(CartItem item) {
        freeItems.add(item);
    }

    public void remove(CartItem item) {
        items.remove(item);
    }

    public void removeFreeItem(CartItem item) {
        freeItems.remove(item);
    }

    public void removeFreeItemIntentionally(CartItem removedItem) {
        if (freeItems.contains(removedItem)) {
            freeItems.remove(removedItem);
            intentionallyRemovedFreeItems.add(removedItem);
        }
    }

    public void addFreeItemBack(CartItem freeRemovedItem) {
        if (itemWasPreviouslyRemoved(freeRemovedItem)) {
            intentionallyRemovedFreeItems.remove(freeRemovedItem);
            freeItems.add(freeRemovedItem);
        }
    }

    public List<CartItem> content() {

        List<CartItem> res = new ArrayList<>();
        res.addAll(items);
        res.addAll(freeItems);

        return res;
    }
    
    private boolean itemWasPreviouslyRemoved(CartItem freeRemovedItem) {
        return intentionallyRemovedFreeItems.contains(freeRemovedItem);
    }

    public static Cart create() {
        return new Cart(CartId.generate());
    }
}
