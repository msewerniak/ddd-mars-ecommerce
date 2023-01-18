package com.mars.ecommerce.sales.domain.product


import spock.lang.Specification

import static com.mars.ecommerce.sales.test.Fixtures.Products.DELL_XPS

class ProductTest extends Specification {

    def productDatabase = Mock(ProductDatabase)
    def factory = new ProductFactory(productDatabase)

    def "should make product snapshot"() {
        given:
        def productId = new ProductId(102011)

        when:
        def product = factory.create("Dell XPS", "Laptops", "The monster!")
        ProductSnapshot snapshot = product.snapshot()

        then:
        productDatabase.nextProductId() >> productId

        snapshot.id() == productId.id()
        snapshot.name() == "Dell XPS"
        snapshot.category() == "Laptops"
        snapshot.description() == "The monster!"
    }

    def "should create product from snapshot"() {

        given:
        def snapshot = DELL_XPS.snapshot()

        expect:
        def product = Product.fromSnapshot(snapshot)
        snapshot.id() == product.id().id()
    }

    def "should return null when trying to create product from null snapshot"() {

        expect:
        null == Product.fromSnapshot(null)
    }
}
