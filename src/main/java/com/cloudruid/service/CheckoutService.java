package com.cloudruid.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudruid.model.Product;
import com.cloudruid.repository.GroceryRepository;

@Service
public class CheckoutService {
	
	@Autowired
	private GroceryRepository groceryRepository;
	
	//Get list of products that the customer has ordered from the input string.
	public List<Product> getOrder(String order){
		String[] orderArr = order.split("\\,");
		List<Product> products = new ArrayList<Product>();
		
		for (String product : orderArr) {
			products.add(groceryRepository.findProdByName(product));
		}
		
		return products;
	}
	
	//The main method of interest of the program (where the bill is calculated)
	public String getBill(List<Product> order) {
		
		//Bill to return.
		double bill = 0;
		
		//This list will store all the products from order that are included in 2 for 3 deal
		List<Product> orderedTwoForThree = new ArrayList<Product>();
		
		//This list will store all the products from order that are included in buy 1 get 1 half price deal.
		List<Product> orderedOneForHalf = new ArrayList<Product>();
		
		for(Product p : order) {
			bill +=p.getPrice();
			
			//If ordered item is contained in deal 2 for 3 add it to list.
			if(groceryRepository.getTwoForThree().contains(p)) {
				orderedTwoForThree.add(p);
			}
			
			//If ordered item is contained in deal 1 add it to the list.
			if (groceryRepository.getOneForHalf().contains(p)) {
				orderedOneForHalf.add(p);
			}
		}
		
		//If ordered products from 2 for 3 offer are < 3 means there is no deal 1 present. 
		//Thus we check for buy 1 get 1 half price deals in the order.
		if(orderedTwoForThree.size() < 3) {
			
			//Scan order for duplicate products that are actually contained in offer buy 1 get 1 half price
			for(int i= 0; i < order.size() - 1; i++) {	
				for (int j = i+1; j <= order.size() - 1;j++) {
					if (order.get(i) == order.get(j) && orderedOneForHalf.contains(order.get(i))) {
						bill -=  (order.get(i).getPrice() * 0.5);
					}
				}
			}
			
		}
		
		//If the number of ordered products from 2 for 3 deal is odd
		if(orderedTwoForThree.size()%3 !=0) {
			//Since the 2 for 3 products are odd take the first three that occur.
			List<Product> subList = orderedTwoForThree.subList(0, 3);
			
			//Get cheapest item and subtract its price from bill.
			double prodMinValue = Collections.min(subList, Comparator.comparing(p -> p.getPrice())).getPrice();
			bill -= prodMinValue;
			
			//Remove sublist from order.
			for (Product p : subList) {
				order.remove(p);
			}
			//Check for offer buy 1 get 1 half price
			for(int i= 0; i < order.size() - 1; i++) {	
				for (int j = i+1; j <= order.size() - 1;j++) {
					if (order.get(i) == order.get(j) && orderedOneForHalf.contains(order.get(i))) {
						bill -=  (order.get(i).getPrice() * 0.5);
					}
				}
			}
			
		}
		
		//If the number of ordered products from 2 for 3 deal is actually even.
		if(orderedTwoForThree.size()%3 ==0) {
			
			while(orderedTwoForThree.size() > 0) {
				List<Product> subList = orderedTwoForThree.subList(0, 3);
				
				//Get cheapest item and subtract its price from bill.
				double prodMinValue = Collections.min(subList, Comparator.comparing(p -> p.getPrice())).getPrice();
				bill -= prodMinValue;
				
				//Remove sublist from order and from list that holds products contained in 2 for 3.
				for (Product p : subList) {
					order.remove(p);
				}
				
				orderedTwoForThree.subList(0,3).clear();
			}
			//Check for offer buy 1 get 1 half price
			for(int i= 0; i < order.size() - 1; i++) {	
				for (int j = i+1; j <= order.size() - 1;j++) {
					if (order.get(i) == order.get(j) && orderedOneForHalf.contains(order.get(i))) {
						bill -=  (order.get(i).getPrice() * 0.5);
					}
				}
			}
		}
		
		// Splitting the price in aws and clouds
		String[] price = String.valueOf(String.format("%.2f", bill)).split("\\.");
		
		if (!price[0].equals("0")) {
			return price[0] + " aws " + price[1] + " c " + "(" + (int)(bill * 100) + " clouds)";
		
		} else {
			return price[1] + " clouds";
		}
	}

}
