package smg.com.example.customerPurchasePoints.Service;

import java.util.Optional;

import smg.com.example.customerPurchasePoints.Entity.Customer;

public interface CustomerService {
    Customer saveCustomer(Customer customer) throws Exception;
    Iterable<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(long id);
    Customer updateCustomer(Customer updatedCustomer);
    void deleteCustomer(long id);
}
