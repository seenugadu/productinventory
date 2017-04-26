package com.productinventory.intg.service;

import com.productinventory.intg.dto.ProductInventory;

import java.util.List;

/**
 * Created by associate on 4/18/17.
 */
public interface ProductInventoryService {

    List<ProductInventory> getInventories();

    ProductInventory getInventoryForProductInStore(Integer sku, Integer storeNbr);

    ProductInventory updateInventoryForProductInStore(ProductInventory productInventory);
}
