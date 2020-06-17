package com.cloudruid.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudruid.model.Product;
import com.cloudruid.repository.GroceryRepository;

@Service
public class CheckoutService {

	@Autowired
	private GroceryRepository groceryRepository;

	// Get list of products that the customer has ordered from the input string.
	public List<Product> getOrder(String order) {

		if (order.isBlank()) {
			return Collections.emptyList();
		} else {

			String[] orderArr = order.split("\\,");
			List<Product> products = new ArrayList<Product>();

			for (String product : orderArr) {
				if (groceryRepository.findAll().contains(groceryRepository.findProdByName(product))) {
					products.add(groceryRepository.findProdByName(product));
				} else {
					return Collections.emptyList();
				}
			}

			return products;
		}
	}

	// The main method of interest of the program (where the bill is calculated)
	public String getBill(List<Product> order) {

		if (order.equals(Collections.emptyList())) {
			return "You did not order anything, or product doesn't exist in our database!";

		} else {
			// Bill to return.
			double bill = 0;

			// This list will store all the products from order that are included in 2 for 3 deal
			ArrayList<Product> orderedTwoForThree = new ArrayList<Product>();

			for (Product p : order) {
				bill += p.getPrice();

				// If ordered item is contained in deal 2 for 3 add it to list.
				if (groceryRepository.getTwoForThree().contains(p)) {
					orderedTwoForThree.add(p);
				}
			}

			// If ordered products from 2 for 3 offer are < 3 means there is no deal 1 present. 
			//Thus we check for buy 1 get 1 half price deals in the order.
			if (orderedTwoForThree.size() < 3) {

				//Scan for the number of frequencies of products from order in offer2 and calculate discount.
				for (Product p : groceryRepository.getOneForHalf()) {
					int frequency = Collections.frequency(order,p);
					
					if (frequency % 2 != 0 && frequency != 1) {
						bill -= ((frequency-1)/2) * (p.getPrice() * 0.5);
					
					} else if (frequency % 2 == 0 && frequency != 0) {
						bill -= (frequency/2) * (p.getPrice() * 0.5);
					}
				}
				// If the number of ordered products from 2 for 3 deal is odd
			} else if (orderedTwoForThree.size() % 3 != 0) {

				// Remove the cheapest item until the products are less than 3.
				while (orderedTwoForThree.size() > 3) {

					// Since the 2 for 3 products are odd take the first three that occur.
					ArrayList<Product> subList = new ArrayList<Product>(orderedTwoForThree.subList(0, 3));

					// Get cheapest item and subtract its price from bill.
					double prodMinValue = Collections.min(subList, Comparator.comparing(p -> p.getPrice())).getPrice();
					bill -= prodMinValue;

					// Remove sublist from order and from array that holds ordered products from this offer.
					order.removeAll(new HashSet<Product>(subList));
					orderedTwoForThree.subList(0, 3).clear();
				}
				// Check for offer buy 1 get 1 half price
				for (Product p : groceryRepository.getOneForHalf()) {
					int frequency = Collections.frequency(order,p);
					
					if (frequency % 2 != 0 && frequency != 1) {
						bill -= ((frequency-1)/2) * (p.getPrice() * 0.5);
					
					} else if (frequency % 2 == 0 && frequency != 0) {
						bill -= (frequency/2) * (p.getPrice() * 0.5);
					}
				}
				// If the number of ordered products from 2 for 3 deal is actually even.
			} else if (orderedTwoForThree.size() % 3 == 0) {

				while (orderedTwoForThree.size() > 0) {
					ArrayList<Product> subList = new ArrayList<Product>(orderedTwoForThree.subList(0, 3));

					// Get cheapest item and subtract its price from bill.
					double prodMinValue = Collections.min(subList, Comparator.comparing(p -> p.getPrice())).getPrice();
					bill -= prodMinValue;

					// Remove sublist from order and from list that holds products contained in 2 for 3.
					order.removeAll(new HashSet<Product>(subList));
					orderedTwoForThree.subList(0, 3).clear();
				}
				// Check for offer buy 1 get 1 half price
				for (Product p : groceryRepository.getOneForHalf()) {
					int frequency = Collections.frequency(order,p);
					
					if (frequency % 2 != 0 && frequency != 1) {
						bill -= ((frequency-1)/2) * (p.getPrice() * 0.5);
					
					} else if (frequency % 2 == 0 && frequency != 0) {
						bill -= (frequency/2) * (p.getPrice() * 0.5);
					}
				}
			}

			// Splitting the price in aws and clouds
			String[] price = String.valueOf(String.format("%.2f", bill)).split("\\.");

			if (!price[0].equals("0")) {
				return price[0] + " aws " + price[1] + " c " + "(" + Math.round((bill * 100)) + " clouds)";

			} else {
				return price[1] + " clouds";
			}
		}
	}
}
