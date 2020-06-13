package com.cloudruid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudruid.model.Product;
import com.cloudruid.repository.GroceryRepository;

@Service
public class ProductService {

	@Autowired
	private GroceryRepository groceryRepository;

	// Add product to the database
	public void addProduct(Product product) {
		groceryRepository.save(product);
	}

	// Get all products
	public List<Product> getProducts() {
		return groceryRepository.findAll();
	}

	// Deletes a product from database according to product id.
	public void deleteProduct(long id) {
		groceryRepository.deleteById(id);
	}
	
	//Find product by name. This will be used to find products from provided user input.
	public Product findProdByName(String productName) {
		return groceryRepository.findProdByName(productName);
	}

}
