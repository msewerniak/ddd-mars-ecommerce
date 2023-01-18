package com.mars.ecommerce.sales.api.rest;

import com.mars.ecommerce.sales.domain.product.Product;
import com.mars.ecommerce.sales.domain.product.ProductDatabase;
import com.mars.ecommerce.sales.domain.product.ProductFactory;
import com.mars.ecommerce.sales.domain.product.ProductId;
import com.mars.ecommerce.sales.domain.product.ProductSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductFactory productFactory;
    private final ProductDatabase productDatabase;

    @GetMapping
    public List<ProductDto> all() {

        List<Product> products = productDatabase.list();

        return products.stream()
                .map(Product::snapshot)
                .map(p -> new ProductDto(p.id(), p.name(), p.category(), p.description()))
                .toList();
    }

    @GetMapping("/{id}")
    public ProductDto find(@PathVariable Long id) {

        ProductSnapshot product = productDatabase.find(new ProductId(id)).snapshot();

        return new ProductDto(product.id(), product.name(), product.category(), product.description());
    }

    @PostMapping()
    public ProductDto add(@RequestBody ProductDto productDto) {

        Product product = productFactory.create(productDto.name, productDto.category, productDto.description);

        productDatabase.saveProduct(product);

        return ProductDto.from(product);
    }

    public record ProductDto(Long id, String name, String category, String description) {
        public static ProductDto from(Product product) {
            return from(product.snapshot());
        }

        public static ProductDto from(ProductSnapshot snapshot) {
            return new ProductDto(snapshot.id(), snapshot.name(), snapshot.category(), snapshot.description());
        }
    }
}
