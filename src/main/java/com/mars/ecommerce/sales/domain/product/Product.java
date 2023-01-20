package com.mars.ecommerce.sales.domain.product;

import com.mars.ecommerce.support.ddd.DddAnnotations;

@DddAnnotations.AggregateRoot
public class Product {

    private final ProductId id;
    private final ProductDetails data;

    public Product(ProductId id, String name, String category, String description) {
        if (id == null) {
            throw new IllegalArgumentException("Product id is required");
        }
        this.id = id;
        this.data = new ProductDetails(name, category, description);
    }

    public static Product fromSnapshot(ProductSnapshot entity) {

        if (entity == null) {
            return null;
        }

        return new Product(new ProductId(entity.id()), entity.name(), entity.category(), entity.description());
    }

    public ProductId id() {
        return id;
    }

    public ProductSnapshot snapshot() {
        return new ProductSnapshot(id.id(), data.name, data.category, data.description);
    }

    private record ProductDetails(String name, String category, String description) {
        private ProductDetails {
            validate(name, category, description);
        }

        private void validate(String name, String category, String description) {
            if (name == null || name.isBlank() || name.length() > 250) {
                throw new IllegalArgumentException("Name must be not blank and not longer than 250 characters");
            }
            if (category == null || category.isBlank() || category.length() > 100) {
                throw new IllegalArgumentException("Category must be not blank and not longer than 100 characters");
            }
            if (description == null || description.isBlank() || description.length() > 1000) {
                throw new IllegalArgumentException("Description must be not blank and not longer than 1000 characters");
            }
        }
    }
}
