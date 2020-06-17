package com.cloudruid.groceries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;

import com.cloudruid.model.Product;
import com.cloudruid.service.CheckoutService;

@SpringBootTest
class CloudruidApplicationTests {
	
	@Autowired
	CheckoutService checkout;
	
	@Test
	//Testing for getting the cheapest item out of 3.
	public void get_Cheapest_Item() {
		final List<Product> product = new ArrayList<Product>(Arrays.asList(	
				 	new Product(null, "apple",0.50, "twoforthree"),
					new Product(null, "banana",0.40, "twoforthree"),
					new Product(null, "tomato",0.30, "twoforthree")));
		 
		double expected = 0.30;
		double actual = Collections.min(product, Comparator.comparing(p -> p.getPrice())).getPrice();
		
		Assert.assertTrue(expected == actual);
	}
	
	//Passing empty list to getBill().
	@Test
	public void pass_Empty_List_Of_Orders() {
		
		//Empty list
		final List<Product> order = new ArrayList<Product>();
		
		final String expected = "You did not order anything, or product doesn't exist in our database!";
		final String actual = checkout.getBill(order);
		
		Assert.assertEquals(actual, expected);
		
	}
	
	//Passing single product to getBill() that is tomato with price 0.30 c.
	@Test
	public void pass_Single_Product_Order() {
		final List<Product> product = Arrays.asList(
				new Product(null, "tomato",0.30, null));
		
		final String expected = "30 clouds";
		final String actual = checkout.getBill(product);
		
		Assert.assertEquals(actual,expected);
	}
	
	//Testing order 1 for half. One item should be half priced.
	@Test
	public void pass_Order_OneForHalf() {
		 final ArrayList<Product> orders = new ArrayList<Product>(Arrays.asList(	
				 	new Product(null, "potato",0.26, "oneforone"),
					new Product(null, "potato",0.26, "oneforone")));
		 
		final String expected = "39 clouds";
		final String actual = checkout.getBill(orders);
		Assert.assertEquals(actual,expected);
	}
	
	//Order that has 2 offers get 1 pay half (even).
	@Test
	public void pass_OneForHalf_Order_Even() {
		final List<Product> products = new ArrayList<Product>(Arrays.asList(
				new Product(null, "potato",0.26, "oneforone"),
				new Product(null, "potato",0.26, "oneforone"),
				new Product(null, "potato",0.26, "oneforone"),
				new Product(null, "potato",0.26, "oneforone")));
		
		final String expected = "78 clouds";
		final String actual = checkout.getBill(products);
		
		Assert.assertEquals(actual,expected);
	}
	
	//Order that has odd numbers of products from buy 1 get 1 half price offer.
	@Test
	public void pass_OneForHalf_Order_Odd() {
		final List<Product> products = new ArrayList<Product>(Arrays.asList(
				new Product(null, "potato",0.26, "oneforone"),
				new Product(null, "potato",0.26, "oneforone"),
				new Product(null, "potato",0.26, "oneforone"),
				new Product(null, "potato",0.26, "oneforone"),
				new Product(null, "potato",0.26, "oneforone")));
		
		final String expected = "1 aws 04 c (104 clouds)";
		final String actual = checkout.getBill(products);
		
		Assert.assertEquals(actual,expected);
	}
	
	//Order that combines different products in multiple buy 1 get 1 half price offers.
	@Test
	public void pass_Multiple_Products_OneForHalf_Orders() {
		final List<Product> products = new ArrayList<Product>(Arrays.asList(
				new Product(null, "potato",0.26, "oneforone"),
				new Product(null, "avocado",1.44, "oneforone"),
				new Product(null, "potato",0.26, "oneforone"),
				new Product(null, "avocado",1.44, "oneforone"),
				new Product(null, "orange",0.20, "oneforone")));
		
		//Expected = 144 + 72 + 26 + 13 + 20 = 275 clouds
		final String expected = "2 aws 75 c (275 clouds)";
		final String actual = checkout.getBill(products);
		
		Assert.assertEquals(actual,expected);
	}
	
	//Testing a order with 2 for 3 deal with even products from offer 1. Cheapest item should be free of charge.
	@Test 
	public void pass_Order_With_TwoForThree_Even() {
		final List<Product> order = new ArrayList<Product>(Arrays.asList(	
				 	new Product(null, "apple",0.50, "twoforthree"),
					new Product(null, "banana",0.40, "twoforthree"),
					new Product(null, "tomato",0.30, "twoforthree")));

		final String expected = "90 clouds";
		final String actual = checkout.getBill(order);
		Assert.assertEquals(actual,expected);
	}
	
	//Testing order with 2 for 3 deal with odd products from offer 1. Only the cheapest should be taken until size < 3.
	@Test
	public void pass_Order_With_ThoForThree_Odd() {
		//7 tomatoes
		final List<Product> order = new ArrayList<Product>(Arrays.asList(	
			 	new Product(null, "tomato",0.30, "twoforthree"),
				new Product(null, "tomato",0.30, "twoforthree"),
				new Product(null, "tomato",0.30, "twoforthree"),
				new Product(null, "tomato",0.30, "twoforthree"),
				new Product(null, "tomato",0.30, "twoforthree"),
				new Product(null, "tomato",0.30, "twoforthree"),
				new Product(null, "tomato",0.30, "twoforthree")));
		
		final String expected = "1 aws 50 c (150 clouds)";
		final String actual = checkout.getBill(order);
		
		Assert.assertEquals(actual,expected);
		
	}
	
	//Testing order without 2 for 3 deal present.
	@Test
	public void pass_Order_Without_TwoForThree() {
		final List<Product> orders = new ArrayList<Product>(Arrays.asList(	
				 	new Product(null, "apple",0.50, "twoforthree"),
					new Product(null, "banana",0.40, "twoforthree"),
					new Product(null, "potato",0.26, "oneforone")));
		 
		final String expected = "1 aws 16 c (116 clouds)";
		final String actual = checkout.getBill(orders);
		Assert.assertEquals(actual,expected);
	}
	
	//Testing order 2 for 3 with the same product. One of them should be free.
	@Test
	public void pass_Order_TwoForThree_SameProduct() {
		 ArrayList<Product> orders = new ArrayList<Product>(Arrays.asList(	
				 	new Product(null, "apple",0.50, "twoforthree"),
					new Product(null, "apple",0.50, "twoforthree"),
					new Product(null, "apple",0.50, "twoforthree")));
		 
		final String expected = "1 aws 00 c (100 clouds)";
		final String actual = checkout.getBill(orders);
		Assert.assertEquals(actual,expected);
	
	}
	
	//Test order with multiple offers included and products not sorted.
	@Test
	public void pass_Order_Multiple_Offers() {
		//Deal 2 for 3 - apple,banana,tomato
		//Deal buy 1 get 1 half price - potato
		 final ArrayList<Product> orders = new ArrayList<Product>(Arrays.asList(	
				 	new Product(null, "apple",0.50, "twoforthree"),
					new Product(null, "potato",0.26, "oneforone"),
					new Product(null, "tomato",0.30, "twoforthree"),
					new Product(null, "banana",0.40, "twoforthree"),
					new Product(null, "potato",0.26, "oneforone"),
					new Product(null, "banana",0.40, "twoforthree")));
		 
		 //Present deals are:
		 //1. apple,tomato,banana : 2 for 3.
		 //2. potato, potato: buy 1 get 1 half price.
		
		 //apple(0.50) + tomato(0) + banana(0.40) + banana(0.40) + 2x potato(0.39) 
		final String expected = "1 aws 69 c (169 clouds)";
		final String actual = checkout.getBill(orders);
		Assert.assertEquals(actual,expected);
	}
	
	//Test multiple offers included in order and products not sorted.
	@Test
	public void pass_Order_Multiple_Offers1() {
		//Deal 2 for 3 - apple,banana,tomato
		//Deal buy 1 get 1 half price - potato
		 final ArrayList<Product> orders = new ArrayList<Product>(Arrays.asList(	
				 	new Product(null, "apple",0.50, "twoforthree"),
					new Product(null, "potato",0.26, "oneforone"),
					new Product(null, "tomato",0.30, "twoforthree"),
					new Product(null, "banana",0.40, "twoforthree"),
					new Product(null, "potato",0.26, "oneforone"),
					new Product(null, "banana",0.40, "twoforthree"),
					new Product(null, "potato",0.26, "oneforone"),
					new Product(null, "apple",0.50, "twoforthree"),
					new Product(null, "banana",0.40, "twoforthree")));
		 
		 //Present deals are:
		 //1. apple,tomato,banana AND banana,apple,banana: 2 for 3. (even number of 2 for 3)
		 //2. potato, potato: buy 1 get 1 half price.
		
//apple(0.50) + tomato(0) + banana(0.40) + banana(0.40) + apple(0.50) + banana(0) + 2x potato(0.39) + potato(0.26) 
		final String expected = "2 aws 45 c (245 clouds)";
		final String actual = checkout.getBill(orders);
		Assert.assertEquals(actual,expected);
	}
	
	//Testing order with not existing product in the database
	@Test
	public void pass_Order_With_Unexisting_Products() {
	final String order = "banana,apple,pineapple";
			
		 final String expected = "You did not order anything, or product doesn't exist in our database!";
		 final String actual = checkout.getBill(checkout.getOrder(order));
		 Assert.assertEquals(actual,expected);
	}
	
	//Testing order that does not correspond to order stated conventions (comma after every product)
	@Test
	public void test_Order_Input() {
		final String order = "banana/tomato,potato/orange";
		
		final String expected = "You did not order anything, or product doesn't exist in our database!";
		final String actual = checkout.getBill(checkout.getOrder(order));
		Assert.assertEquals(actual,expected);
	}
	
	//Test valid offer string
	@Test
	public void pass_Valid_Offer_String() {
		final String order = "banana,tomato,potato";
		
		final List<Product> expected = new ArrayList<Product>(Arrays.asList(	
			 	new Product(null, "banana",0.40, "twoforthree"),
				new Product(null, "tomato",0.30, "twoforthree"),
				new Product(null, "potato",0.26, "oneforone")));
		
		final List<Product> actual = checkout.getOrder(order);
		Assert.assertEquals(actual,expected);
	}
	
	//Testing subList if performs as expected - taking the first 3 items from list.
	@Test
	public void test_subList() {
		 final List<Product> orderedTwoForThree = new ArrayList<Product>(Arrays.asList(	
				 	new Product(null, "apple",0.20, "twoforthree"),
					new Product(null, "tomato",0.40, "twoforthree"),
					new Product(null, "cucumber",0.70, "twoforthree"),
					new Product(null, "pineapple",0.40, "twoforthree")));
		 
		 final List<Product> expected = new ArrayList<Product>(Arrays.asList(	
				 	new Product(null, "apple",0.20, "twoforthree"),
					new Product(null, "tomato",0.40, "twoforthree"),
					new Product(null, "cucumber",0.70, "twoforthree")));
		 
		 final List<Product> actual = orderedTwoForThree.subList(0, 3);
		 
		 Assert.assertEquals(actual,expected);
	}
	
	//Testing if list clear call truncates the right items.
	@Test
	public void test_Delete_Prod_From_List() {
		 final List<Product> actual = new ArrayList<Product>(Arrays.asList(	
				 	new Product(null, "apple",0.20, "twoforthree"),
					new Product(null, "tomato",0.40, "twoforthree"),
					new Product(null, "cucumber",0.70, "twoforthree"),
					new Product(null, "pineapple",0.40, "twoforthree")));
		 
		 final List<Product> expected = new ArrayList<Product>(Arrays.asList(
					new Product(null, "pineapple",0.40, "twoforthree")));
		 
		 actual.subList(0, 3).clear();
		 
		 Assert.assertEquals(actual,expected);
	}
}
