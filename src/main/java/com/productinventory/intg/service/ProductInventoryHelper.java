package com.productinventory.intg.service;

import com.productinventory.intg.dto.Inventory;
import com.productinventory.intg.dto.Product;
import com.productinventory.intg.dto.ProductInventory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by associate on 4/18/17.
 */
@Component
public class ProductInventoryHelper {

    Logger LOGGER = Logger.getLogger(ProductInventoryHelper.class);

    @Value("${product.url}")
    String productUrl;

    @Value("${inventory.url}")
    String inventoryUrl;

    @Autowired
    RestTemplate restTemplate;

    /**
     * Get all product inventories.
     *
     * @return
     */
    public List<ProductInventory> getProductInventories(){

        List<Inventory> inventories = getInventories();

        List<Product> products = getProducts();

        List<ProductInventory> productInventories = new ArrayList<>();

        for(Inventory inventory : inventories){
            ProductInventory productInventory = new ProductInventory();
            productInventory.setSku(inventory.getSku());
            productInventory.setStoreNbr(inventory.getStoreNbr());
            productInventory.setQuantity(inventory.getQuantity());

            products.forEach(item->{
                if(item.getSku().equals(inventory.getSku())){
                    productInventory.setName(item.getName());
                }
            });

            productInventories.add(productInventory);
        }

        return productInventories;
    }

    /**
     * Get a product inventory for sku and store
     *
     * @param sku
     * @param storeNbr
     * @return
     */
    public ProductInventory getProductInventory(Integer sku, Integer storeNbr){
        Product product = getProduct(sku);
        Inventory inventory = getInventory(sku, storeNbr);
        ProductInventory productInventory = new ProductInventory();
        productInventory.setSku(product.getSku());
        productInventory.setName(product.getName());
        productInventory.setStoreNbr(inventory.getStoreNbr());
        productInventory.setQuantity(inventory.getQuantity());
        return productInventory;
    }

    /**
     * Get all products
     *
     * @return
     */
    public List<Product> getProducts(){

        LOGGER.debug("get all products");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(productUrl)
                .path("/products");

        HttpEntity entity = getHeaders();

        ResponseEntity<List<Product>> rateResponse =
                restTemplate.exchange(builder.toUriString(),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<Product>>() {
                        });
        List<Product> products = rateResponse.getBody();

        return products;
    }

    /**
     * Get all inventories
     *
     * @return
     */
    public List<Inventory> getInventories(){

        LOGGER.debug("get all inventories");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(inventoryUrl)
                .path("/inventories");

        HttpEntity entity = getHeaders();

        ResponseEntity<List<Inventory>> rateResponse =
                restTemplate.exchange(builder.toUriString(),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<Inventory>>() {
                        });
        List<Inventory> inventories = rateResponse.getBody();

        return inventories;
    }

    /**
     * Get a product for sku
     *
     * @param sku
     * @return
     */
    private Product getProduct(Integer sku){

        LOGGER.debug("get product for sku");

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(productUrl)
                .path("/products/{sku}").buildAndExpand(String.valueOf(sku));

        //Create the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Product> productResponseEntity = restTemplate.exchange(uriComponents.toUriString(),HttpMethod.GET, entity, Product.class);

        return productResponseEntity.getBody();
    }

    /**
     * Get inventory for sku and store.
     *
     * @param sku
     * @param storeNbr
     * @return
     */
    private Inventory getInventory(Integer sku, Integer storeNbr){

        LOGGER.debug("get inventory for sku and store");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(inventoryUrl)
                .path("/inventory")
                .queryParam("sku", sku)
                .queryParam("storeNbr", String.valueOf(storeNbr));

        HttpEntity entity = getHeaders();

        ResponseEntity<Inventory> inventoryResponseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,entity, Inventory.class);

        return inventoryResponseEntity.getBody();
    }


    /**
     * Adds a product inventory to store.
     *
     * @param productInventory
     * @return
     */
    public ProductInventory addOrUpdateProductInventory(ProductInventory productInventory){

        Product product = populateProduct(productInventory);
        addProduct(product);
        Inventory inventory = populateInventory(productInventory);
        addInventory(inventory);

        return productInventory;

    }


    /**
     * This method adds a product
     *
     * @param product
     * @return
     */
    private Product addProduct(Product product){

        LOGGER.debug("add product");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(productUrl)
                .path("/addProduct");

        //Create the request entity
        HttpEntity<Product> requestEntity = new HttpEntity<Product>(product, null);

        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(builder.toUriString(), requestEntity, Product.class);

        return responseEntity.getBody();
    }

    /**
     * This method adds inventory to store
     *
     * @param inventory
     * @return
     */
    private Inventory addInventory(Inventory inventory){

        LOGGER.debug("add invetory for store");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(inventoryUrl)
                .path("/inventories");

        //Create the request entity
        HttpEntity<Inventory> requestEntity = new HttpEntity<Inventory>(inventory, null);

        ResponseEntity<Inventory> responseEntity = restTemplate.postForEntity(builder.toUriString(), requestEntity, Inventory.class);

        return responseEntity.getBody();
    }

    /**
     * Populates a product
     *
     * @param productInventory
     * @return
     */
    private Product populateProduct(ProductInventory productInventory){
        Product product = new Product();
        product.setSku(productInventory.getSku());
        product.setName(productInventory.getName());
        return product;
    }

    /**
     * Populates an inventory
     *
     * @param productInventory
     * @return
     */
    private Inventory populateInventory(ProductInventory productInventory){
        Inventory inventory = new Inventory();
        inventory.setSku(productInventory.getSku());
        inventory.setStoreNbr(productInventory.getStoreNbr());
        inventory.setQuantity(productInventory.getQuantity());
        return inventory;
    }

    private HttpEntity getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        return new HttpEntity(headers);
    }
}
