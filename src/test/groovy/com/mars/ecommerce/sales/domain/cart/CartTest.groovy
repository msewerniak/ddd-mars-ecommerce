package com.mars.ecommerce.sales.domain.cart

import com.mars.ecommerce.sales.domain.prices.Price
import com.mars.ecommerce.sales.infrastructure.repository.hashmap.ExtraItemsPolicyInMemory
import com.mars.ecommerce.sales.test.Fixtures
import spock.lang.Specification

class CartTest extends Specification {

    CartItem DELL_XPS = new CartItem(Fixtures.Products.DELL_XPS.id(), Price.euro(1000))
    CartItem BAG = new CartItem(Fixtures.Products.BAG.id(), Price.ZERO)
    
    ExtraItemsPolicy extraItemsPolicy = new ExtraItemsPolicyInMemory()

    def "Can add an item"() {
        given:
        Cart cart = aCart()

        when:
        // DELL_XPS added to the cart

        then:
        // cart items are [DELL_XPS]
    }

    def "Can add two same items"() {
        given:
        Cart cart = aCart()

        when:
        // DELL_XPS added to the cart
        // DELL_XPS added to the cart

        then:
        // cart items are [DELL_XPS, DELL_XPS]
    }

    def "Can remove an item"() {
        given:
        Cart cart = aCart()
        
        and:
        // DELL_XPS added to the cart

        when:
        // DELL_XPS removed from the cart

        then:
        // cart items are []
    }

    def "Can remove two same items"() {
        given:
        Cart cart = aCart()

        and:
        // DELL_XPS added to the cart
        // DELL_XPS added to the cart

        when:
        // DELL_XPS removed from the cart
        // DELL_XPS removed from the cart

        then:
        // cart items []
    }

    def "When adding a laptop, bag is added for free"() {
        given:
        Cart cart = aCart()

        and:
        freeBagDiscountIsEnabled()

        when:
        // DELL_XPS added to the cart

        then:
        // cart items [[DELL_XPS, BAG]
    }

    def "Two laptops means 2 bags"() {
        given:
        Cart cart = aCart()

        and:
        freeBagDiscountIsEnabled()

        when:
        // DELL_XPS added to the cart
        // DELL_XPS added to the cart

        then:
        // cart items [DELL_XPS, DELL_XPS, BAG, BAG]
    }

    def "Can remove free bag"() {
        given:
        Cart cart = aCart()

        and:
        freeBagDiscountIsEnabled()

        when:
        // DELL_XPS added to the cart
        // BAG intentionally removed from the cart

        then:
        // cart items [DELL_XPS]
    }

    def "Can remove just one free bag"() {
        given:
        Cart cart = aCart()

        and:
        freeBagDiscountIsEnabled()

        when:
        // DELL_XPS added to the cart
        // DELL_XPS added to the cart
        // BAG intentionally removed from the cart

        then:
        // cart items [DELL_XPS, DELL_XPS, BAG]
    }

    def "I want my free bag back"() {
        given:
        Cart cart = aCart()

        and:
        freeBagDiscountIsEnabled()

        when:
        // DELL_XPS added to the cart
        // DELL_XPS added to the cart
        // BAG intentionally removed from the cart
        // BAG added back free item

        then:
        // cart items [DELL_XPS, DELL_XPS, BAG, BAG]
    }

    def "Already has 2 free bags (and 2 laptops), wants just one new bag!"() {
        given:
        Cart cart = aCart()
        and:
        freeBagDiscountIsEnabled()

        when:
        // DELL_XPS added to the cart
        // DELL_XPS added to the cart
        // BAG added again but not free item

        then:
        // cart items [DELL_XPS, DELL_XPS, BAG, BAG, BAG]
    }

    def "Has 2 free bags, removes one, adds it back, adds additional bag, removes it and adds back and removes"() {
        given:
        Cart cart = aCart()

        and:
        freeBagDiscountIsEnabled()

        when:
        // DELL_XPS added to the cart
        // DELL_XPS added to the cart
        // BAG intentionally removed from the cart
        // BAG added back free item
        // BAG added again but not free item
        // BAG removed from the cart
        // BAG added again but not free item
        // BAG removed from the cart

        then:
        // cart items [DELL_XPS, DELL_XPS, BAG, BAG]
    }

    def freeBagDiscountIsEnabled() {
        extraItemsPolicy.add(DELL_XPS.productId(), BAG.productId())
    }

    Cart aCart() {
        Cart view = Cart.create()
        cartRepository.save(view)
        return view
    }

}