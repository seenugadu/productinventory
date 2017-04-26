package com.productinventory.intg.dto;

/**
 * Created by associate on 4/18/17.
 */
public class ProductInventory {

    private Integer sku;
    private String name;
    private Integer storeNbr;
    private Integer quantity;

    public Integer getSku() {
        return sku;
    }

    public void setSku(Integer sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStoreNbr() {
        return storeNbr;
    }

    public void setStoreNbr(Integer storeNbr) {
        this.storeNbr = storeNbr;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
