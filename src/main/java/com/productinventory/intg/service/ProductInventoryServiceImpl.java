package com.productinventory.intg.service;

import com.productinventory.intg.dto.ProductInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by associate on 4/18/17.
 */
@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

    @Autowired
    ProductInventoryHelper helper;

    @Override
    public List<ProductInventory> getInventories() {
        return helper.getProductInventories();
    }

    @Override
    public ProductInventory getInventoryForProductInStore(Integer sku, Integer storeNbr) {
        return helper.getProductInventory(sku, storeNbr);
    }

    @Override
    public ProductInventory updateInventoryForProductInStore(ProductInventory productInventory) {
        return helper.addOrUpdateProductInventory(productInventory);
    }


}
