package com.productinventory.intg.controller;

import com.productinventory.intg.dto.ProductInventory;
import com.productinventory.intg.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by associate on 4/18/17.
 */
@RestController
@RequestMapping("/")
public class ProductInventoryController {

    @Autowired
    ProductInventoryService productInventoryService;

    @RequestMapping(value="productinventory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ProductInventory getInventoryForProductInStore(@RequestParam("sku") Integer sku, @RequestParam("storeNbr") Integer storeNbr){
        return productInventoryService.getInventoryForProductInStore(sku, storeNbr);
    }

    @RequestMapping(value="productinventories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ProductInventory> getAllInventories(){
        return productInventoryService.getInventories();
    }

    @RequestMapping(value="addproductinventory", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ProductInventory addInventory(@RequestBody ProductInventory productInventory){
        return productInventoryService.updateInventoryForProductInStore(productInventory);
    }
}
