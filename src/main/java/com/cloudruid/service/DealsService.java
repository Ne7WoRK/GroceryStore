package com.cloudruid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudruid.model.Product;
import com.cloudruid.repository.GroceryRepository;

@Service
public class DealsService {
	
	@Autowired
	private GroceryRepository groceryRepository;
	
	// Returns all products that are included in deal 1.
		public List<Product> getTwoForThree() {
			return groceryRepository.getTwoForThree();
		}
		
	//Returns all products that are included in deal 2.
		public List<Product> getOneForHalf() {
			return groceryRepository.getOneForHalf();
		}
		
	//Get all product that do not have offers.
		public List<Product> getNoneOffer() {
			return groceryRepository.getNoneOffer();
		}

}
