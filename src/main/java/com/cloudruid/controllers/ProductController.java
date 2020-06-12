package com.cloudruid.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloudruid.model.Product;
import com.cloudruid.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	//Controller that renders the view (groceries.html) template and populates tables with content.
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String index(Model model, Product product) {

			List<Product> products = productService.getProducts();
			List<Product> twoForThree = productService.getTwoForThree();
			List<Product> oneForOne = productService.getOneForOne();
			
			
			model.addAttribute ("products", products);
			model.addAttribute("product", product);
			model.addAttribute("twoForThree", twoForThree);
			model.addAttribute("oneForOne", oneForOne);

		return "groceries";
	}
	
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