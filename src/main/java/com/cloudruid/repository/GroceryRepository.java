package com.cloudruid.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cloudruid.model.Product;

@Repository
public interface GroceryRepository extends JpaRepository<Product, Long>{
	
	@Query(value = "SELECT * FROM products WHERE offer = 'twoforthree'", nativeQuery = true)
	List<Product> getTwoForThree();
	
	@Query(value = "SELECT * FROM products WHERE offer = 'oneforone'", nativeQuery = true)
	List<Product> getOneForOne();
	
	@Query(value = "SELECT * FROM products WHERE name = ?1", nativeQuery = true)
	Product findProdByName(String productName);

}
