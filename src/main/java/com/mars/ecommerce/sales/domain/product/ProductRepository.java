package com.mars.ecommerce.sales.domain.product;

import java.util.List;

public interface ProductRepository {

    void save(ProductSnapshot product);

    List<ProductSnapshot> list();

    ProductSnapshot find(Long id);

    Long nextId();
}
