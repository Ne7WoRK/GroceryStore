package com.cloudruid.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloudruid.model.Product;
import com.cloudruid.service.ProductService;

@Controller
public class ProductController {
	//Autowiring deals service.
	@Autowired 
	ProductService productService;
	
	
	//Controller to handle adding of new product to the database.
	@RequestMapping(value = "/products/add", method = RequestMethod.POST)
	public String addProduct(Product product,BindingResult result) {
		
		productService.addProduct(product);
		
		return "redirect:/products";
	}
	
	//Deleting product from database controller.
	@RequestMapping(value = "/products/delete/{id}", method = RequestMethod.POST)
	public String deleteProduct(@PathVariable(name = "id") int id) {
		
		productService.deleteProduct(id);
		
		return "redirect:/products";
	}
}