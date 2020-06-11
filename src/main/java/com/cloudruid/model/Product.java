package com.cloudruid.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "products")
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double price;
	private String offer;
	
	public Product(Long id, String name, double price) {
		this.id = id;
		this.setName(name);
		this.setPrice(price);
	}
	
	public Product() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	 public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public boolean equals(Object o) {
	       
		 if (o == this) { 
	            return true; 
	        }
		 
	       if (!(o instanceof Product)) { 
	            return false; 
	        }
	       
	       Product p = (Product) o; 
	        return name.equals(p.name) && price == p.price; 
	 }
	
	public String toString(){
		
		//Splitting the price in aws and clouds
		String[] price = String.valueOf(String.format("%.2f", getPrice())).split("\\.");
		
		if (!price[0].equals("0")) {
			return "Name: " + getName() + "\nPrice: " + price[0] + " aws " + price[1] + " c " + "(" + getPrice()*100+" clouds)" ;
		} else {
			return "Name: " + getName() + "\nPrice: " + price[1] +"c";
		}
	}
	
	

}
