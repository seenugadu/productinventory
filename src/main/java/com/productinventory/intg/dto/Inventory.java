package com.productinventory.intg.dto;

/**
 * Created by associate on 4/18/17.
 */
public class Inventory {

    private Integer sku;
    private Integer storeNbr;
    private Integer quantity;

    public Integer getSku() {
        return sku;
    }

    public void setSku(Integer sku) {
        this.sku = sku;
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
