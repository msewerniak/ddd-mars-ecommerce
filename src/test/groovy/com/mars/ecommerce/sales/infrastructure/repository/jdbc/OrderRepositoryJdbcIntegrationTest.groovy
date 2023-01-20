package com.mars.ecommerce.sales.infrastructure.repository.jdbc

import com.mars.ecommerce.sales.domain.cart.Cart
import com.mars.ecommerce.sales.domain.cart.CartId
import com.mars.ecommerce.sales.domain.cart.CartItem
import com.mars.ecommerce.sales.domain.cart.CartRepository
import com.mars.ecommerce.sales.domain.client.Client
import com.mars.ecommerce.sales.domain.client.ClientId
import com.mars.ecommerce.sales.domain.client.ClientSnapshot
import com.mars.ecommerce.sales.domain.delivery.DeliveryData
import com.mars.ecommerce.sales.domain.discounts.DiscountFactory
import com.mars.ecommerce.sales.domain.offer.Offer
import com.mars.ecommerce.sales.domain.order.Order
import com.mars.ecommerce.sales.domain.order.OrderFactory
import com.mars.ecommerce.sales.domain.order.OrderId
import com.mars.ecommerce.sales.domain.order.OrderItem
import com.mars.ecommerce.sales.domain.order.OrderRepository
import com.mars.ecommerce.sales.domain.prices.Price
import com.mars.ecommerce.sales.domain.product.ProductId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class OrderRepositoryJdbcIntegrationTest extends Specification {

    @Autowired
    OrderRepository cartRepository
    
    @Autowired
    DiscountFactory discountFactory
    
    @Autowired
    OrderFactory orderFactory

    def "should save and retrieve cart"() {
        
        given:
        def cart = new Cart(CartId.generate())
        cart.add(new CartItem(new ProductId(100001), Price.euro(100L)))

        ClientSnapshot clientData = new Client(ClientId.generate(), "Joe").snapshot()
        DeliveryData deliveryData = new DeliveryData("some address")
        
        Offer calculatedOffer = cart.calculateOffer(discountFactory.create())
        
        def order = orderFactory.createOrder(cart.id(), calculatedOffer, deliveryData, clientData)
        
        when:
        cartRepository.save(order.snapshot())
        def dbOrder = cartRepository.find(order.id().id())
        
        then:
        dbOrder == order.snapshot()
    }

}