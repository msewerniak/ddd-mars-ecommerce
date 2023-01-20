package com.mars.ecommerce.sales.domain.offer;

import com.mars.ecommerce.sales.domain.discounts.DiscountPolicy;
import com.mars.ecommerce.sales.domain.prices.Price;

import java.util.List;

public record Offer(List<OfferItem> offerItems, DiscountPolicy appliedDiscount, String currency) {

    public Price totalPrice() {
        return Price.euro(offerItems.stream().map(OfferItem::totalPrice).mapToLong(Price::value).sum());
    }

    public Price totalPriceAfterDiscount() {
        return appliedDiscount.apply(this);
    }

    public boolean sameAs(Offer seenOffer) {
        // TODO: calculation if the offer differs from the other offer
        return seenOffer != null;
    }
}
