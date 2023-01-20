package com.mars.ecommerce.sales.domain.prices;

import com.mars.ecommerce.sales.domain.product.ProductId;
import com.mars.ecommerce.support.ddd.DddAnnotations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@DddAnnotations.DomainService
@Service
public class Pricing {

    Map<ProductId, Price> prices = new HashMap<>();

    public void add(ProductId productId, Price price) {
        prices.put(productId, price);
    }

    public Price productPrice(ProductId productId) {
        return Optional.of(prices.get(productId)).orElse(Price.ZERO);
    }
}
