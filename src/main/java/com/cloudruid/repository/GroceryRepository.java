package com.cloudruid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudruid.model.Product;

@Repository
public interface GroceryRepository extends JpaRepository<Product, Integer>{

}
