package smg.com.example.customerPurchasePoints.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import smg.com.example.customerPurchasePoints.Entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	Optional<Customer> findByLastNameAndFirstName(String lastName, String firstName);
	Customer findById(long id);
}
