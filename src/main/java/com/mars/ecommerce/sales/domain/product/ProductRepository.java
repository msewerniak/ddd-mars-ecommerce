package com.mars.ecommerce.sales.domain.product;

import com.mars.ecommerce.support.ddd.DddAnnotations;

import java.util.List;

@DddAnnotations.DomainRepository
public interface ProductRepository {

    void save(ProductSnapshot product);

    List<ProductSnapshot> list();

    ProductSnapshot find(Long id);

    Long nextId();
}
