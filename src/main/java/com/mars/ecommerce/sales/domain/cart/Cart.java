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
        // TODO: implement me
    }

    public void addFree(CartItem item) {
        // TODO: implement me
    }

    public void remove(CartItem item) {
        // TODO: implement me
    }

    public void removeFreeItem(CartItem item) {
        // TODO: implement me
    }

    public void removeFreeItemIntentionally(CartItem removedItem) {
        // TODO: implement me
    }

    public void addFreeItemBack(CartItem freeRemovedItem) {
        // TODO: implement me
    }

    public List<CartItem> content() {

        List<CartItem> res = new ArrayList<>();
        res.addAll(items);
        res.addAll(freeItems);

        return res;
    }

    
    public static Cart create() {
        return new Cart(CartId.generate());
    }
}
