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
	CustomerPurchase findByPurchaseDateBetween(Date purchaseDateTo, Date purchaseDateFrom);

//	@Query("select customer.id, month, year, sum(purchaseAmount) " +
//	       "from CustomerPurchase cp " +
//		   "where cp.purchaseDate >= ?1 and cp.purchaseDate <= ?2 " +
//	       "group by cp.customer, cp.year, cp.month")
//	public List<Integer> getPurchByPurchDateGroupByCustYearMonth(Date purchDateStart, Date purchaseDateEnd);

}

