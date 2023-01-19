package com.mars.ecommerce.sales.domain.cart

import com.mars.ecommerce.sales.domain.prices.Price
import com.mars.ecommerce.sales.infrastructure.repository.hashmap.ExtraItemsPolicyInMemory
import com.mars.ecommerce.sales.test.Fixtures
import spock.lang.Specification

class CartTest extends Specification {

    CartRepository cartRepository = new CartHashMapRepository()
    ExtraItemsPolicy extraItemsPolicy = new ExtraItemsPolicyInMemory()

    CartService cartService = new CartService(cartRepository, extraItemsPolicy)

    CartItem DELL_XPS = new CartItem(Fixtures.Products.DELL_XPS.id(), Price.euro(1000))
    CartItem BAG = new CartItem(Fixtures.Products.BAG.id(), Price.ZERO)

    def "Can add an item"() {
        given:
        Cart cart = aCart()

        when:
        cartService.addItem(cart.id(), DELL_XPS)

        then:
        [DELL_XPS] == cart.content()
    }

    def "Can add two same items"() {
        given:
        Cart cart = aCart()

        when:
        cartService.addItem(cart.id(), DELL_XPS)
        cartService.addItem(cart.id(), DELL_XPS)

        then:
        [DELL_XPS, DELL_XPS] == cart.content()
    }

    def "Can remove an item"() {
        given:
        Cart cart = aCart()
        and:
        cartService.addItem(cart.id(), DELL_XPS)

        when:
        cartService.removeItem(cart.id(), DELL_XPS)

        then:
        [] == cart.content()
    }

    def "Can remove two same items"() {
        given:
        Cart cart = aCart()

        and:
        cartService.addItem(cart.id(), DELL_XPS)
        cartService.addItem(cart.id(), DELL_XPS)

        when:
        cartService.removeItem(cart.id(), DELL_XPS)
        cartService.removeItem(cart.id(), DELL_XPS)

        then:
        [] == cart.content()
    }

    def "When adding a laptop, bag is added for free"() {
        given:
        Cart cart = aCart()

        and:
        freeBagDiscountIsEnabled()

        when:
        cartService.addItem(cart.id(), DELL_XPS)

        then:
        [DELL_XPS, BAG] == cart.content()
    }

    def "Two laptops means 2 bags"() {
        given:
        Cart cart = aCart()

        and:
        freeBagDiscountIsEnabled()

        when:
        cartService.addItem(cart.id(), DELL_XPS)
        cartService.addItem(cart.id(), DELL_XPS)

        then:
        [DELL_XPS, DELL_XPS, BAG, BAG] == cart.content()
    }

    def "Can remove free bag"() {
        given:
        Cart cart = aCart()

        and:
        freeBagDiscountIsEnabled()

        when:
        cartService.addItem(cart.id(), DELL_XPS)
        cartService.intentionallyRemoveFreeItem(cart.id(), BAG)

        then:
        [DELL_XPS] == cart.content()
    }

    def "Can remove just one free bag"() {
        given:
        Cart cart = aCart()

        and:
        freeBagDiscountIsEnabled()

        when:
        cartService.addItem(cart.id(), DELL_XPS)
        cartService.addItem(cart.id(), DELL_XPS)
        cartService.intentionallyRemoveFreeItem(cart.id(), BAG)

        then:
        [DELL_XPS, DELL_XPS, BAG] == cart.content()
    }

    def "I want my free bag back"() {
        given:
        Cart cart = aCart()

        and:
        freeBagDiscountIsEnabled()

        when:
        cartService.addItem(cart.id(), DELL_XPS)
        cartService.addItem(cart.id(), DELL_XPS)
        cartService.intentionallyRemoveFreeItem(cart.id(), BAG)
        cartService.addFreeItemBack(cart.id(), BAG)

        then:
        [DELL_XPS, DELL_XPS, BAG, BAG] == cart.content()
    }

    def "Already has 2 free bags (and 2 laptops), wants just one new bag!"() {
        given:
        Cart cart = aCart()
        and:
        freeBagDiscountIsEnabled()

        when:
        cartService.addItem(cart.id(), DELL_XPS)
        cartService.addItem(cart.id(), DELL_XPS)
        cartService.addItem(cart.id(), BAG)

        then:
        [DELL_XPS, DELL_XPS, BAG, BAG, BAG] == cart.content()
    }

    def "Has 2 free bags, removes one, adds it back, adds additional bag, removes it and adds back and removes"() {
        given:
        Cart cart = aCart()

        and:
        freeBagDiscountIsEnabled()

        when:
        cartService.addItem(cart.id(), DELL_XPS)
        cartService.addItem(cart.id(), DELL_XPS)
        cartService.intentionallyRemoveFreeItem(cart.id(), BAG)
        cartService.addFreeItemBack(cart.id(), BAG)
        cartService.addItem(cart.id(), BAG)
        cartService.removeItem(cart.id(), BAG)
        cartService.addItem(cart.id(), BAG)
        cartService.removeItem(cart.id(), BAG)

        then:
        [DELL_XPS, DELL_XPS, BAG, BAG] == cart.content()
    }

    def "Try to hack the system and call addFreeItemBackCommand"() {
        given:
        Cart cart = aCart()

        when:
        cartService.addFreeItemBack(cart.id(), BAG)

        then:
        [] == cart.content()
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