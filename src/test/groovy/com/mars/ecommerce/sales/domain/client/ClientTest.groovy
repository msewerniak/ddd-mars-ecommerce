package com.mars.ecommerce.sales.domain.client

import spock.lang.Specification

class ClientTest extends Specification {

    def "should not create client with blank name"() {

        when:
        new Client(ClientId.generate(), "")

        then:
        thrown(IllegalArgumentException)

    }

    def "should not update name longer than 50 characters"() {
        given:
        def client = new Client(ClientId.generate(), "Joe")

        when:
        client.updateName('J' * 51)

        then:
        thrown(IllegalArgumentException)
    }


}
