package com.cloudruid.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "price", nullable = false)
	private double price;
	
	@Column(name = "offer", nullable = true)
	private String offer;

	public Product(Long id, String name, double price, String offer) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.offer = "";
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
		BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
		double roundedPrice = bd.doubleValue();

		return roundedPrice;
	}

	public void setPrice(double price) {
		BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
		double roundedPrice = bd.doubleValue();
		this.price = roundedPrice;
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

	public String toString() {

		// Splitting the price in aws and clouds
		String[] price = String.valueOf(String.format("%.2f", getPrice())).split("\\.");

		if (!price[0].equals("0")) {
			return "Name: " + getName() + "\nPrice: " + price[0] + " aws " + price[1] + " c " + "(" + (int)(getPrice() * 100)
					+ " clouds)" + "\nOffer: " + getOffer();
		} else {
			return "Name: " + getName() + "\nPrice: " + price[1] + "clouds" + "\nOffer: " + getOffer();
		}
	}

}
