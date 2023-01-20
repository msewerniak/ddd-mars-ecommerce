package com.mars.ecommerce.sales.domain.cart;

import com.mars.ecommerce.sales.domain.prices.Price;
import com.mars.ecommerce.sales.domain.product.ProductId;
import com.mars.ecommerce.support.ddd.DddAnnotations;

@DddAnnotations.ValueObject
public record CartItem(ProductId productId, Price price) {

    public static CartItem free(ProductId productId) {
        return new CartItem(productId, Price.ZERO);
    }
}
