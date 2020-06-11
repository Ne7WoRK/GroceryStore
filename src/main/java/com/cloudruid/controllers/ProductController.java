package com.cloudruid.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloudruid.model.Product;
import com.cloudruid.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String index(Model model) {

			List<Product> products = productService.getProducts();
			model.addAttribute ("products", products);

		return "groceries";
	}

}