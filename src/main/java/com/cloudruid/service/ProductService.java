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
	
	//Add product to the database
	public void addOfficer(Product product) {
		groceryRepository.save(product);
	}
	
	//Delete product from database
	public void deleteOfficer (Product product) {
		groceryRepository.delete(product);
	}
	
	//Get all products
	public List<Product> getProducts(){
		List<Product> products = new ArrayList<Product>(groceryRepository.findAll());
		return products;
	}

}
