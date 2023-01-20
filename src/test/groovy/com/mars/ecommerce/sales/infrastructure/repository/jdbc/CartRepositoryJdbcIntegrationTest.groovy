package com.mars.ecommerce.sales.infrastructure.repository.jdbc

import com.mars.ecommerce.sales.domain.cart.Cart
import com.mars.ecommerce.sales.domain.cart.CartId
import com.mars.ecommerce.sales.domain.cart.CartItem
import com.mars.ecommerce.sales.domain.cart.CartRepository
import com.mars.ecommerce.sales.domain.prices.Price
import com.mars.ecommerce.sales.domain.product.ProductId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CartRepositoryJdbcIntegrationTest extends Specification {

    @Autowired
    CartRepository cartRepository

    def "should save and retrieve cart"() {
        
        given:
        CartId cartId = CartId.generate()
        def cart = new Cart(cartId)
        cart.add(new CartItem(new ProductId(100001), Price.euro(100L)))

        when:
        cartRepository.save(cart)
        def cartFromRepo = cartRepository.find(cartId)
        
        then:
        cartFromRepo.content() == cart.content()
    }

}