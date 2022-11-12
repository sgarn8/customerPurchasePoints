package smg.com.example.customerPurchasePoints.Service;

import java.util.List;
import java.util.Optional;

import smg.com.example.customerPurchasePoints.Entity.CustomerPurchase;

public interface CustomerPurchaseService {
    CustomerPurchase saveCustomerPurchase(CustomerPurchase CustomerPurchase);
    List<CustomerPurchase> getAllCustomerPurchases();
    Optional<CustomerPurchase> getCustomerPurchaseById(long id);
    CustomerPurchase updateCustomerPurchase(CustomerPurchase updatedCustomerPurchase);
    void deleteCustomerPurchase(long id);
}
