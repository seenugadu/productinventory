package com.productinventory.intg.dto;

import java.io.Serializable;

/**
 * Created by associate on 4/18/17.
 */
public class Product implements Serializable{

    private Integer sku;
    private String name;

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
}
