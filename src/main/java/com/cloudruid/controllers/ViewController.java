package com.cloudruid.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloudruid.model.Product;
import com.cloudruid.service.DealsService;
import com.cloudruid.service.ProductService;

@Controller
public class ViewController {
	
	//Autowiring deal service.
	@Autowired
	private DealsService dealService;
	
	//Autowiring product service.
	@Autowired 
	ProductService productService;
	
	//Controller that renders the view (groceries.html) template and populates tables with content.
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String index(Model model, Product product) {
		
		//All products available	
		List<Product> products = productService.getProducts();
		
		//List that returns products with no offer associated to them
		List<Product> noffer = dealService.getNoneOffer();
		
		//Products contained in deal 1.
		List<Product> twoForThree = dealService.getTwoForThree();
		
		//Products contained in deal 2.
		List<Product> oneForOne = dealService.getOneForHalf();
			
			//Sending data to the view template (thymeleaf).
			model.addAttribute ("products", products);
			model.addAttribute("product", product);
			model.addAttribute("twoForThree", twoForThree);
			model.addAttribute("oneForOne", oneForOne);
			model.addAttribute("noffer", noffer);
			model.addAttribute("bill", "0");

		return "groceries";
	}

}
