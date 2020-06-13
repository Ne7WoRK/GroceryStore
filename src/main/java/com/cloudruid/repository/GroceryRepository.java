package com.cloudruid.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cloudruid.model.Product;

@Repository
public interface GroceryRepository extends JpaRepository<Product, Long>{
	
	//Find product by name.
	@Query(value = "SELECT * FROM products WHERE name = ?1", nativeQuery = true)
	Product findProdByName(String productName);
	
	//Get all products from offer 1.
	@Query(value = "SELECT * FROM products WHERE offer = 'twoforthree'", nativeQuery = true)
	List<Product> getTwoForThree();
	
	//Get all products included in offer 2.
	@Query(value = "SELECT * FROM products WHERE offer = 'oneforone'", nativeQuery = true)
	List<Product> getOneForHalf();
	
	//Get all products that do not have offer associated to them.
	@Query(value = "SELECT * FROM `products` WHERE offer IS NULL or offer =''", nativeQuery = true)
	List<Product> getNoneOffer();
	
	

}
