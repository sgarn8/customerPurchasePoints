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
public class CustomerReportServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;
    
    @InjectMocks
    private CustomerReportService customerReportService;

    private Customer customer;

    @BeforeEach
    public void setup(){
    	
    }

    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for calcRptEndDate for 2022-10-15")
    @Test
    public void calcRptEndDate20221015(){
    	
    	LocalDate rptDate = LocalDate.of(2022, Month.OCTOBER, 15);
    	assertEquals(LocalDate.of(2022, Month.OCTOBER, 31),customerReportService.getRptEndDate(rptDate));
    }
    
    @DisplayName("JUnit test for calcRptEndDate for 2022-10-15")
    @Test
    public void calcRptEndDate20221001(){
    	
    	LocalDate rptDate = LocalDate.of(2022, Month.OCTOBER, 1);
    	assertEquals( LocalDate.of(2022, Month.OCTOBER, 31), customerReportService.getRptEndDate(rptDate));
    }
    
    @DisplayName("JUnit test for calcRptEndDate for 2022-10-31")
    @Test
    public void calcRptEndDate20221031(){
    	
    	LocalDate rptDate = LocalDate.of(2022, Month.OCTOBER, 31);
    	assertEquals(LocalDate.of(2022, Month.OCTOBER, 31), customerReportService.getRptEndDate(rptDate));
    }
    
    @DisplayName("JUnit test for calcRptStartDate for 2022-10-31")
    @Test
    public void calcRptStarrDate20221031(){	
    	LocalDate rptDate = LocalDate.of(2022, Month.OCTOBER, 31);
    	int rptDurationMonths = 3;
    	assertEquals(LocalDate.of(2022, Month.AUGUST, 1), customerReportService.getRptStartDate(rptDurationMonths, rptDate));
    }
    
    @DisplayName("JUnit test for calcRptStartDate for 2022-10-15")
    @Test
    public void calcRptStarrDate20221015(){	
    	LocalDate rptDate = LocalDate.of(2022, Month.OCTOBER, 15);
    	int rptDurationMonths = 3;
    	assertEquals(LocalDate.of(2022, Month.AUGUST, 1), customerReportService.getRptStartDate(rptDurationMonths, rptDate));
    }
    
    @DisplayName("JUnit test for calcRptStartDate for 2022-10-01")
    @Test
    public void calcRptStarrDate20221001(){	
    	LocalDate rptDate = LocalDate.of(2022, Month.OCTOBER, 01);
    	int rptDurationMonths = 3;
    	assertEquals(LocalDate.of(2022, Month.AUGUST, 1), customerReportService.getRptStartDate(rptDurationMonths, rptDate));
    }
    
    @DisplayName("JUnit test for calcRptStartDate for 2023-01-15")
    @Test
    public void calcRptStarrDate20220115(){	
    	LocalDate rptDate = LocalDate.of(2023, Month.JANUARY, 15);
    	int rptDurationMonths = 3;
    	assertEquals(LocalDate.of(2022, Month.NOVEMBER, 1), customerReportService.getRptStartDate(rptDurationMonths, rptDate));
    }
}
