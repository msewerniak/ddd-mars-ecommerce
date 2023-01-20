package com.mars.ecommerce.sales

import com.mars.ecommerce.sales.api.service.OrderingService
import com.mars.ecommerce.sales.domain.cart.CartId
import com.mars.ecommerce.sales.domain.delivery.DeliveryData
import com.mars.ecommerce.sales.domain.offer.Offer
import com.mars.ecommerce.sales.domain.order.Order
import com.mars.ecommerce.sales.domain.order.OrderDatabase
import com.mars.ecommerce.sales.domain.order.OrderId
import com.mars.ecommerce.sales.domain.prices.Price
import com.mars.ecommerce.sales.domain.prices.Pricing
import com.mars.ecommerce.sales.domain.product.ProductDatabase
import com.mars.ecommerce.sales.domain.product.ProductId
import com.mars.ecommerce.sales.domain.product.ProductSnapshot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class SalesIntegrationTest extends Specification {

    @Autowired
    Pricing pricing

    @Autowired
    ProductDatabase productDatabase

    @Autowired
    OrderDatabase orderDatabase

    @Autowired
    OrderingService orderingService

    def "should save products"() {
        when:
        def products = productDatabase.list()

        then:
        products.size() == 4
        products.get(0).snapshot() == new ProductSnapshot(100000, "xbox series x", "gaming consoles", "best of the best")
    }

    def "client can add products to the shopping cart and confirm order"() {

        given:
        ProductId xboxProductId = new ProductId(100000L)
        ProductId padProductId = new ProductId(100001L)

        Price xboxPrice = Price.euro(600)
        Price padPrice = Price.euro(45)

        pricing.add(xboxProductId, xboxPrice)
        pricing.add(padProductId, padPrice)

        when:
        CartId cartId = orderingService.createOrder()

        orderingService.addProductToCart(cartId, xboxProductId)
        orderingService.addProductToCart(cartId, padProductId)

        then:
        Offer offer = orderingService.calculateOffer(cartId)

        Price appliedDiscount = Price.euro(10)
        offer.totalPriceAfterDiscount() == xboxPrice.add(padPrice).subtract(appliedDiscount)

        when:
        OrderId orderId = orderingService.confirm(cartId, offer, new DeliveryData("some delivery address"))

        then:
        Order order = orderDatabase.find(orderId)

        then:
        order.status() == Order.OrderStatus.CONFIRMED

        order.snapshot().paymentStatus() == Order.PaymentStatus.STARTED.name()
    }

}