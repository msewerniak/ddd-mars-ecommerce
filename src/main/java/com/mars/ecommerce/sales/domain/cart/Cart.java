package com.mars.ecommerce.sales.domain.cart;

import com.mars.ecommerce.sales.domain.discounts.DiscountPolicy;
import com.mars.ecommerce.sales.domain.offer.Amount;
import com.mars.ecommerce.sales.domain.offer.Offer;
import com.mars.ecommerce.sales.domain.offer.OfferItem;
import com.mars.ecommerce.sales.domain.prices.Price;
import com.mars.ecommerce.sales.domain.product.ProductId;
import com.mars.ecommerce.support.ddd.DddAnnotations;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DddAnnotations.AggregateRoot
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


    public static Cart fromSnapshot(CartSnapshot cartSnapshot) {

        Cart cart = new Cart(new CartId(cartSnapshot.id()));

        cartSnapshot.items().forEach(snapshot -> {
            CartItem item =
                    new CartItem(new ProductId(snapshot.productId()), new Price(snapshot.price(), snapshot.currency()));
            switch (snapshot.type()) {
            case ITEM -> cart.items.add(item);
            case FREE_ITEM -> cart.freeItems.add(item);
            case INTENTIONALLY_REMOVED_FREE_ITEM -> cart.intentionallyRemovedFreeItems.add(item);
            }
        });

        return cart;
    }

    public CartSnapshot snapshot() {

        List<CartSnapshot.CartItemSnapshot> cartItemSnapshots = new ArrayList<>();

        cartItemSnapshots.addAll(snapshot(items, CartSnapshot.CartItemSnapshot.CartItemType.ITEM));
        cartItemSnapshots.addAll(snapshot(freeItems, CartSnapshot.CartItemSnapshot.CartItemType.FREE_ITEM));
        cartItemSnapshots.addAll(snapshot(intentionallyRemovedFreeItems,
                CartSnapshot.CartItemSnapshot.CartItemType.INTENTIONALLY_REMOVED_FREE_ITEM));

        return new CartSnapshot(id.id(), cartItemSnapshots);
    }

    private List<CartSnapshot.CartItemSnapshot> snapshot(List<CartItem> items,
            CartSnapshot.CartItemSnapshot.CartItemType type) {
        return items.stream()
                .map(i -> new CartSnapshot.CartItemSnapshot(id.id(), i.productId().id(), i.price().value(),
                        i.price().currency(),
                        type))
                .toList();
    }

    public Offer calculateOffer(DiscountPolicy discountPolicy) {

        Map<ProductId, OfferItem> offerItems = new HashMap<>();

        for (CartItem item : content()) {
            ProductId productId = item.productId();
            OfferItem offerItem = offerItems.get(productId);

            if (offerItem != null) {
                offerItems.put(productId,
                        new OfferItem(offerItem.productId(), Price.min(item.price(), offerItem.price()),
                                offerItem.amount().increase()));
            } else {
                offerItems.put(productId, new OfferItem(productId, item.price(), Amount.ZERO.increase()));
            }
        }

        return new Offer(offerItems.values().stream().toList(), discountPolicy, "EUR");
    }
}
