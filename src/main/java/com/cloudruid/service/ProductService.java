package com.cloudruid.service;

import java.util.ArrayList;
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
		List<Product> products = new ArrayList<Product>(groceryRepository.findAll());
		return products;
	}

	// Deletes a product from database according to product id.
	public void deleteProduct(long id) {
		groceryRepository.deleteById(id);

	}

	// Returns all products that are included in deal 1.
	public List<Product> getTwoForThree() {
		List<Product> twoForThree = new ArrayList<Product>(groceryRepository.getTwoForThree());
		return twoForThree;
	}
	
	//Returns all products that are included in deal 2.
	public List<Product> getOneForOne() {
		List<Product> oneForOne = new ArrayList<Product>(groceryRepository.getOneForOne());
		return oneForOne;
	}
	
	//Find product by name. This will be used to find products from provided user input.
	public Product findProdByName(String productName) {
		Product product = groceryRepository.findProdByName(productName);
		
		return product;
	}

}
