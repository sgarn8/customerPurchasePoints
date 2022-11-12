package smg.com.example.customerPurchasePoints.ServiceImpl;

import java.util.Optional;

import smg.com.example.customerPurchasePoints.Entity.Customer;
import smg.com.example.customerPurchasePoints.Repository.CustomerRepository;
import smg.com.example.customerPurchasePoints.Service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository CustomerRepository) {
        this.customerRepository = CustomerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) throws Exception {

        Optional<Customer> savedCustomer = customerRepository.findByLastNameAndFirstName(customer.getLastName(), customer.getFirstName());
        if(savedCustomer.isPresent()){
            throw new Exception("Customer already exist with given Lastname:" + customer.getLastName());
        }
        return customerRepository.save(customer);
    }

    @Override
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(long id) {
        return Optional.of(customerRepository.findById(id));
    }

    @Override
    public Customer updateCustomer(Customer updatedCustomer) {
        return customerRepository.save(updatedCustomer);
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }

}
