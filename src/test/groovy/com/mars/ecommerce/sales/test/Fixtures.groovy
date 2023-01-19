package com.mars.ecommerce.sales.test

import com.mars.ecommerce.sales.domain.prices.Price
import com.mars.ecommerce.sales.domain.product.Product
import com.mars.ecommerce.sales.domain.product.ProductId

class Fixtures {

    static class Products {

        static final Product DELL_XPS = new Product(new ProductId(100001), "Dell XPS", "Laptops", "The monster!")
        static final Product LOGITECH_MOUSE = new Product(new ProductId(100002), "Logitech mouse", "Computer accessories", "Great mouse")
        static final Product BAG = new Product(new ProductId(900001), "Bag", "Computer accessories", "Nice bag")
    }

    static class Prices {
        static final CURRENCY_EURO = "EUR"
        static final EURO_5 = Price.euro(5)
        static final EURO_10 = Price.euro(10)
        static final EURO_20 = Price.euro(20)
        static final EURO_100 = Price.euro(100)
        static final EURO_105 = Price.euro(105)
        static final EURO_1000 = Price.euro(1000)
        static final DOLLAR_100 = new Price(100, "USD")
    }

}
