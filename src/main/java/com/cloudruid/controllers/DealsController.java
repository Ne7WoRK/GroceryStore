package com.cloudruid.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudruid.model.Product;
import com.cloudruid.service.ProductService;

@Controller
public class DealsController {
	
	//Autowiring product service.
		@Autowired
		private ProductService productService;
	
	//Controller to add product to 2 for 3 deal.
	@RequestMapping(value = "/products/addoffer/twoforthree/{product}",method = RequestMethod.POST)
	public String addTwoForThree(@RequestParam (name = "product") String productName ) {
		
		Product p = productService.findProdByName(productName);
		p.setOffer("twoforthree");
		productService.addProduct(p);
		return "redirect:/products";	
	}
	
	//Controller to add product to 1 get 1 half price deal.
	@RequestMapping(value = "/products/addoffer/oneforhalf/{name}",method = RequestMethod.POST)
	public String addOneForHalf(@RequestParam(name = "product") String productName ) {
		
		Product p = productService.findProdByName(productName);
		p.setOffer("oneforone");
		productService.addProduct(p);
		return "redirect:/products";
	}
	
	//Controller that will remove offers from products
	@RequestMapping(value = "/products/removeoffer/{product}", method = RequestMethod.POST)
	public String deleteOffer(@RequestParam (name = "product") String productName) {
		Product p = productService.findProdByName(productName);
		p.setOffer("");
		productService.addProduct(p);
		return "redirect:/products";
	}
}
