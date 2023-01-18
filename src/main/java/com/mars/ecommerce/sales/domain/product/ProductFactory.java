package com.mars.ecommerce.sales.domain.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProductFactory {

    private final ProductDatabase productDatabase;

    public Product create(String name, String category, String description) {
        return new Product(generatedId(), name, category, description);
    }

    private ProductId generatedId() {
        return productDatabase.nextProductId();
    }

}
