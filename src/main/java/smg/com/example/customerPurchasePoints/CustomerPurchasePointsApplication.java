package smg.com.example.customerPurchasePoints;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import smg.com.example.customerPurchasePoints.Entity.CustomerActivity;
import smg.com.example.customerPurchasePoints.mapper.CustomerActivityMapper;

@SpringBootApplication
public class CustomerPurchasePointsApplication {
	public static void main(String[] args) {
		SpringApplication.run(CustomerPurchasePointsApplication.class, args);
	}
}
