package com.mars.ecommerce.sales.domain.offer

import spock.lang.Specification

class AmountTest extends Specification {

    def "should not create amount lower than zero"() {

        when:
        new Amount(-2)

        then:
        thrown(IllegalArgumentException)
    }

    def "should increase amount by one"() {

        expect:
        new Amount(5) == new Amount(4).increase()
    }
}
