package com.mars.ecommerce.sales.api.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.hamcrest.Matchers.notNullValue
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class ProductsControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    def "should add product to the product catalog with rest endpoint"() {
        given:
        def product = """{ "name": "iphone 14", "category": "mobile phones", "description": "brand new and beautiful" } """

        expect:
        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(product))
                .andExpect(status().is2xxSuccessful()).andExpect(jsonPath('$.id', notNullValue()))
    }
}
