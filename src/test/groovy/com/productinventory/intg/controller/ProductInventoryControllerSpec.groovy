package com.productinventory.intg.controller

import com.productinventory.intg.dto.ProductInventory
import com.productinventory.intg.service.ProductInventoryService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * Created by associate on 4/24/17.
 */
class ProductInventoryControllerSpec extends Specification{

    private ProductInventoryService productInventoryService

    private MockMvc mockMvc

    def setup(){
        productInventoryService = Mock()
        def controllerTest = new ProductInventoryController(productInventoryService: productInventoryService)
        mockMvc = MockMvcBuilders.standaloneSetup(controllerTest).build()
    }

    def "GET a product inventory for sku and store"(){

        given: "SKU and store number"
            def sku = 110
            def storeNbr = 9001
            def productName = 'Demo Product'
            def count = 10
            def productInventory = new ProductInventory(sku: 110, name: "Demo Product", storeNbr: 9001, quantity: 10)

        when: "Fetch product inventory for sku and store"

        def result = mockMvc.perform(get("/productinventory")
                    .param("sku", "110")
                    .param("storeNbr", "9001")
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

        then: "Mock the service to fetch get inventory for product and store"
            productInventoryService.getInventoryForProductInStore(sku, storeNbr) >> productInventory

            result.andExpect(status().isOk())
            result.andExpect(jsonPath("\$.sku").value(sku))
            result.andExpect(jsonPath("\$.storeNbr").value(storeNbr))
            result.andExpect(content().string(containsString(productName)))
            result.andExpect(jsonPath("\$.quantity").value(count))


    }

    def "Throws RestClientException with 400 BAD REQUEST as request is missing a param"(){

        given: "SKU and store number"
            def sku = 110
            def storeNbr = 9001
            def productName = 'Demo Product'
            def count = 10
            def productInventory = new ProductInventory(sku: 110, name: "Demo Product", storeNbr: 9001, quantity: 10)

        when: "Fetch product inventory for sku and store"

        def result = mockMvc.perform(get("/productinventor")
                    .param("sku", "110")
                    .param("storeNbr", "9001")
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

        then: "Mock the service to fetch get inventory for product and store"
            productInventoryService.getInventoryForProductInStore(sku, storeNbr) >> productInventory

            result.andExpect(status().is4xxClientError())

    }



    def "GET all product inventory"(){

        given: "2 product inventories"
            def productInventory1 = new ProductInventory(sku: 110, name: "Demo Product1", storeNbr: 9001, quantity: 10)
            def productInventory2 = new ProductInventory(sku: 111, name: "Demo Product2", storeNbr: 9002, quantity: 20)
            List<ProductInventory> inventoryList = new ArrayList<>()
            inventoryList.add(productInventory1)
            inventoryList.add(productInventory2)

        when: "Fetch all product inventory"

            def result = mockMvc.perform(get("/productinventories")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

        then: "Mock the service to fetch all inventories"
            productInventoryService.getInventories() >> inventoryList

            result.andExpect(status().isOk())
            result.andExpect(jsonPath("\$.[1].sku").value(111))
            result.andExpect(jsonPath("\$.[1].storeNbr").value(9002))
            result.andExpect(jsonPath("\$.[1].name").value("Demo Product2"))
            result.andExpect(jsonPath("\$.[1].quantity").value(20))


    }

    def "ADD a product inventory"(){

        given: "Set the json body request"
            def jsonBody = "{\"sku\":110, \"name\":\"Demo Product\",\"storeNbr\":9001,\"quantity\":12}"
            def productInventory = new ProductInventory(sku: 110, name: "Demo Product", storeNbr: 9001, quantity: 12)

        when: "Add a product inventory"

            def result = mockMvc.perform(post("/addproductinventory")
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(jsonBody.getBytes()))


        then: "Mock the service to add a product inventory"
            productInventoryService.updateInventoryForProductInStore(_ as ProductInventory) >> productInventory

            result.andExpect(status().isOk())
            result.andExpect(jsonPath("\$.sku").value(110))
            result.andExpect(jsonPath("\$.storeNbr").value(9001))
            result.andExpect(jsonPath("\$.name").value("Demo Product"))
            result.andExpect(jsonPath("\$.quantity").value(12))
    }
}
