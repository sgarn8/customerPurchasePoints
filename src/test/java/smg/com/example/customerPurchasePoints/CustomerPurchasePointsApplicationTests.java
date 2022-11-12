package smg.com.example.customerPurchasePoints;

import java.sql.Date;
import java.time.LocalDate;

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
		
		long millis=System.currentTimeMillis();  
        LocalDate today= LocalDate.now();
		double amount = 85.00;
		
		CustomerPurchase customerPurchase = 
				new CustomerPurchase(customer, today, amount);	
	}

}
