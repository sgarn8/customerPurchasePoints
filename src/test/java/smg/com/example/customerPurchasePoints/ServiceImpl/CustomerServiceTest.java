package smg.com.example.customerPurchasePoints.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import smg.com.example.customerPurchasePoints.Entity.Customer;
import smg.com.example.customerPurchasePoints.Repository.CustomerRepository;
import smg.com.example.customerPurchasePoints.ServiceImpl.CustomerReportService;
import smg.com.example.customerPurchasePoints.ServiceImpl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;
    
    @InjectMocks
    private CustomerReportService customerReportService;

    private Customer customer=null;

    @BeforeEach
    public void setup(){

		customer = new Customer("John", "Doe");

    }

    // JUnit test for saveEmployee method
//    @DisplayName("JUnit test for saveEmployee method")
//    @Test
//    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
//        // given - precondition or setup
//        given(employeeRepository.findByEmail(employee.getEmail()))
//                .willReturn(Optional.empty());
//
//        given(employeeRepository.save(employee)).willReturn(employee);
//
//        System.out.println(employeeRepository);
//        System.out.println(employeeService);
//
//        // when -  action or the behavior that we are going test
//        Employee savedEmployee = employeeService.saveEmployee(employee);
//
//        System.out.println(savedEmployee);
//        // then - verify the output
//        assertThat(savedEmployee).isNotNull();
//    }
}
