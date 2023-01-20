package com.mars.ecommerce.sales.domain.offer;

import com.mars.ecommerce.sales.domain.prices.Price;
import com.mars.ecommerce.sales.domain.product.ProductId;

public record OfferItem(ProductId productId, Price price, Amount amount) {

    Price totalPrice() {
        assert price.currency().equals("EUR");  // currently we only support EUR

        return price.multipleBy(amount);
    }
}
