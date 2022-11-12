package smg.com.example.customerPurchasePoints.Repository;

import java.sql.Date;
import java.util.List;

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.SUM;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import smg.com.example.customerPurchasePoints.Entity.Customer;
import smg.com.example.customerPurchasePoints.Entity.CustomerPurchase;

@Repository
public interface CustomerPurchaseRepository extends CrudRepository<CustomerPurchase, Long> {
	List<CustomerPurchase> findByCustomer(Customer customer);
	CustomerPurchase findById(long id);
	List<CustomerPurchase> findAllByPurchaseDateBetween(Date purchaseDateTo, Date purchaseDateFrom);
}

