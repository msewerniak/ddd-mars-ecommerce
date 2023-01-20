package com.mars.ecommerce.sales.domain.product;

import com.mars.ecommerce.support.ddd.DddAnnotations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@DddAnnotations.DomainFactory
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
