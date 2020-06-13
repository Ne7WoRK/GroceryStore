package com.cloudruid.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudruid.model.Product;
import com.cloudruid.service.CheckoutService;
import com.cloudruid.service.DealsService;
import com.cloudruid.service.ProductService;

@Controller
public class CheckoutController {
	
	//Autowiring product service.
	@Autowired 
	ProductService productService;
	
	//Autowiring checkout service.
	@Autowired
	private CheckoutService checkout;
	
	//Autowiring deal service.
	@Autowired
	private DealsService dealService;
	
	@RequestMapping(value="/products/checkout", method = RequestMethod.POST)
	public String getBill(@RequestParam("checkout") String order, Model model) {
		
		//All products available	
		List<Product> products = productService.getProducts();
		
		//Products contained in deal 1.
		List<Product> twoForThree = dealService.getTwoForThree();
		
		//Products contained in deal 2.
		List<Product> oneForOne = dealService.getOneForHalf();
		
		//List of ordered products from frontend.
		List<Product> orderProducts = checkout.getOrder(order);
		
		//Just to show real order in the view.
		List<Product> ordereIs = checkout.getOrder(order);
		
		
		//Sending data to the view template (thymeleaf).
		model.addAttribute ("products", products);
		model.addAttribute("twoForThree", twoForThree);
		model.addAttribute("oneForOne", oneForOne);
		model.addAttribute("orderProd", ordereIs);
		
		String finalBill = checkout.getBill(orderProducts);
		
		model.addAttribute("bill", finalBill);
		
		
		return "checkout";
	}

}
