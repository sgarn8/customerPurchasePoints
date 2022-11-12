package smg.com.example.customerPurchasePoints;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import smg.com.example.customerPurchasePoints.Entity.Customer;
import smg.com.example.customerPurchasePoints.Entity.CustomerPurchase;

@SpringBootTest
class CustomerPurchasePointsApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void storeCustomerPurchase() {
		Customer customer = new Customer("John", "Doe");
		Long millis = System.currentTimeMillis();
		Date today = new Date(millis);
		double amount = 85.00;
		
		CustomerPurchase customerPurchase = 
				new CustomerPurchase(customer, today, amount);	
	}

}
