package com.mars.ecommerce.sales.domain.product;

import com.mars.ecommerce.support.ddd.DddAnnotations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@DddAnnotations.DomainRepository
@RequiredArgsConstructor
@Repository
public class ProductDatabase {

    private final ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product.snapshot());
    }

    public List<Product> list() {
        return productRepository.list().stream().map(Product::fromSnapshot).toList();
    }

    public Product find(ProductId id) {
        return Product.fromSnapshot(productRepository.find(id.id()));
    }

    public ProductId nextProductId() {
        return new ProductId(productRepository.nextId());
    }
}
