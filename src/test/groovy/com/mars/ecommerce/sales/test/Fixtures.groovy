package com.mars.ecommerce.sales.test


import com.mars.ecommerce.sales.domain.product.Product
import com.mars.ecommerce.sales.domain.product.ProductId

class Fixtures {

    static class Products {

        static final Product DELL_XPS = new Product(new ProductId(100001), "Dell XPS", "Laptops", "The monster!")
        static final Product LOGITECH_MOUSE = new Product(new ProductId(100002), "Logitech mouse", "Computer accessories", "Great mouse")
        static final Product BAG = new Product(new ProductId(900001), "Bag", "Computer accessories", "Nice bag")
    }

}
