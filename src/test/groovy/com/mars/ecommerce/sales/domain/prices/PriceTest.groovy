package com.mars.ecommerce.sales.domain.prices

import com.mars.ecommerce.sales.domain.offer.Amount
import spock.lang.Specification

import static com.mars.ecommerce.sales.test.Fixtures.Prices.*

class PriceTest extends Specification {

    def "should return lower price from two given"() {

        expect:
        EURO_100 == Price.min(EURO_105, EURO_100)
    }

    def "should fail when one or the other price is null"() {

        when:
        Price.min(EURO_10, null)

        then:
        thrown IllegalArgumentException

        when:
        Price.min(null, EURO_10)

        then:
        thrown IllegalArgumentException
    }

    def "should fail when one price is in different currency than the other"() {

        when:
        EURO_100.lowerThan(DOLLAR_100)

        then:
        thrown IllegalArgumentException
    }

    def "should return true when price is lower than the other"() {

        expect:
        EURO_100.lowerThan(EURO_105)
    }

    def "should return false when price is not lower than the other"() {

        expect:
        !EURO_10.lowerThan(EURO_10)
    }

    def "should add and subtract price"() {

        expect:
        EURO_105 == EURO_100.add(EURO_5)

        and:
        EURO_100 == EURO_105.subtract(EURO_5)
    }

    def "should multiple price by the amount"() {

        expect:
        EURO_100 == EURO_10.multipleBy(new Amount(10))
    }

    def "should fail to multiple price when amount is null"() {

        when:
        EURO_10.multipleBy(null)

        then:
        thrown IllegalArgumentException
    }

}
