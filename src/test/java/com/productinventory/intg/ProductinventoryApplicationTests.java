package com.productinventory.intg;

import com.productinventory.intg.dto.ProductInventory;
import com.productinventory.intg.service.ProductInventoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductinventoryApplicationTests {

	@Autowired
	ProductInventoryService productInventoryService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testProductInventory(){
		addProductInventory();
		getInventories();
		getInventoryForProductInStore();
	}

	private void getInventories(){
		List<ProductInventory> productInventoryList = productInventoryService.getInventories();
		assertNotNull(productInventoryList);
		assertTrue(productInventoryList.size() > 0);
	}

	private void addProductInventory(){

		ProductInventory productInventory = new ProductInventory();
		productInventory.setSku(110);
		productInventory.setName("Demo Product");
		productInventory.setStoreNbr(9001);
		productInventory.setQuantity(11);
		ProductInventory productInventory1 = productInventoryService.updateInventoryForProductInStore(productInventory);
		assertNotNull(productInventory1);
		assertTrue(productInventory1.getSku() == 110);
		assertTrue(productInventory1.getName().equals("Demo Product"));
		assertTrue(productInventory1.getQuantity() == 11);
		assertTrue(productInventory1.getStoreNbr() == 9001);
	}

	private void getInventoryForProductInStore(){

		ProductInventory productInventory = productInventoryService.getInventoryForProductInStore(110, 9001);
		assertNotNull(productInventory);
		assertTrue(productInventory.getSku() == 110);
		assertTrue(productInventory.getName().equals("Demo Product"));
		assertTrue(productInventory.getQuantity() == 11);
		assertTrue(productInventory.getStoreNbr() == 9001);
	}

}
