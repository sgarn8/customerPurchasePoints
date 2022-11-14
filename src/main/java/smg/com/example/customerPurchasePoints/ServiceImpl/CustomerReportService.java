package smg.com.example.customerPurchasePoints.ServiceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import smg.com.example.customerPurchasePoints.Entity.Customer;
import smg.com.example.customerPurchasePoints.Entity.CustomerActivity;
import smg.com.example.customerPurchasePoints.Entity.CustomerPurchase;
import smg.com.example.customerPurchasePoints.Repository.CustomerPurchaseRepository;
import smg.com.example.customerPurchasePoints.Repository.CustomerRepository;
import smg.com.example.customerPurchasePoints.Response.CustomerPurchaseSummary;
import smg.com.example.customerPurchasePoints.Response.CustomerReport;
import smg.com.example.customerPurchasePoints.Response.MonthlySummary;
import smg.com.example.customerPurchasePoints.mapper.CustomerActivityMapper;

@Service
public class CustomerReportService {
	@Autowired
	CustomerPurchaseRepository customerPurchaseRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	private static boolean fileLoaded = false;
	
	public CustomerReport produceCustomerReport(String inputFilename, LocalDate reportDate, int rptDurInMonths) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String title = rptDurInMonths + " Month Report based on input file: " + inputFilename;
		CustomerReport customerReport = new CustomerReport(title);
			    
		LocalDate rptStartDate = getRptStartDate(rptDurInMonths, reportDate);
		LocalDate rptEndDate = getRptEndDate(reportDate);
		customerReport.setStartDate(rptStartDate.format(formatter));
		customerReport.setEndDate(rptEndDate.format(formatter));
		
		loadCustomerActivityFromFile(inputFilename);
		Map<Long, CustomerPurchaseSummary> customerPurchaseSummaryMap = summarizeCustomerPurchasesByMonth(
				rptStartDate, rptEndDate);
		customerReport.setCustomerPurchaseSummaries(customerPurchaseSummaryMap);
		
		return customerReport;
	}

	private Map<Long, CustomerPurchaseSummary> summarizeCustomerPurchasesByMonth(
			LocalDate startDate, LocalDate endDate) {
		
		Map<Long, CustomerPurchaseSummary> customerPurchaseSummaryMap = new HashMap<Long, CustomerPurchaseSummary>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM");
		CustomerPurchaseSummary customerPurchaseSummary = null;
		Map<String, MonthlySummary> custMonthlySummariesMap = null;
		MonthlySummary monthlySummary=null;
		
 		for (Customer customer : customerRepository.findAll(Sort.by("LastName").ascending().and(Sort.by("FirstName").ascending()))) {
 			Long custId = customer.getId();

			for (CustomerPurchase customerpurchase : 				
				customerPurchaseRepository.findAllByCustomerAndPurchaseDateBetweenOrderByPurchaseDateAsc(
					customer, Date.valueOf(startDate), Date.valueOf(endDate))) {				
	

				LocalDate purchDate = customerpurchase.getPurchaseDate().toLocalDate();
				String monthlyPurchaseKey = purchDate.format(formatter);
				
				if (customerPurchaseSummaryMap.containsKey(custId)) {
					// Customer summary exists so use it
					customerPurchaseSummary = customerPurchaseSummaryMap.get(custId);
					custMonthlySummariesMap = customerPurchaseSummary.getMonthlySummaries();
					
					// check if monthly summary map already contains month-key
					if ( custMonthlySummariesMap != null 
					&& custMonthlySummariesMap.containsKey(monthlyPurchaseKey)) {
						// Get Customer Monthly summary if it exists
						monthlySummary = custMonthlySummariesMap.get(monthlyPurchaseKey);
					} else {
						// otherwise add new Customer Monthly summary
						monthlySummary = new MonthlySummary(purchDate);
						custMonthlySummariesMap.put(monthlyPurchaseKey, monthlySummary);

					}
				} else {
					// create new customer summary 
					customerPurchaseSummary = new CustomerPurchaseSummary();
					customerPurchaseSummaryMap.put(custId, customerPurchaseSummary);					
			
					customerPurchaseSummary.setCustomerId(custId);					
					custMonthlySummariesMap = new HashMap<>();
					
					monthlySummary = new MonthlySummary(purchDate);					
					customerPurchaseSummary.setMonthlySummaries(custMonthlySummariesMap);
					customerPurchaseSummary.setTotalPoints(0D);		
					
					// add monthly summary map to newly created customerPurchaseSummary
					custMonthlySummariesMap.put(monthlyPurchaseKey, monthlySummary);
					
				}
	
				// Determine points to add
				int pointsToAdd=0;
				if (customerpurchase.getPurchaseAmt()>100) {
					pointsToAdd = (int) ((customerpurchase.getPurchaseAmt() - 100D) * 2D + 50);
					monthlySummary.setPoints(monthlySummary.getPoints() + pointsToAdd);
				} else if (customerpurchase.getPurchaseAmt()>50) {
					pointsToAdd = (int) (customerpurchase.getPurchaseAmt() - 50D);
					monthlySummary.setPoints(monthlySummary.getPoints() + pointsToAdd);
				}
				
				// Add points to customer's total points
				customerPurchaseSummary.setTotalPoints(customerPurchaseSummary.getTotalPoints() + pointsToAdd);
				
				monthlySummary.setPurchaseTotal(monthlySummary.getPurchaseTotal() + customerpurchase.getPurchaseAmt());

				customerPurchaseSummary.setCustomerName(
						customer.getFirstName() + " " + customer.getLastName() );
			}	
		}
		return customerPurchaseSummaryMap;
	}
	
	public void loadCustomerActivityFromFile(String inputFilename) {
		if (!fileLoaded) {
			CustomerActivity customerActivity;
			CustomerActivityMapper customerActivityMapper = new CustomerActivityMapper(inputFilename);
		    customerActivity = customerActivityMapper.getCustomerActivity();
		    customerActivity.updatePurchMonthAndDate();
		    
		    for (CustomerPurchase customerPurchase  : customerActivity.getCustomerPurchases()) {
		    	Optional<Customer> currentCustomer = customerRepository.findByLastNameAndFirstName(
		    			customerPurchase.getCustomer().getLastName(), 
		    			customerPurchase.getCustomer().getFirstName());
		    	if (currentCustomer.isPresent()) {
		    		customerPurchase.setCustomer(currentCustomer.get());
		    	}
				customerPurchaseRepository.save(customerPurchase);
		    }
		    fileLoaded=true;
		}
	}

	protected LocalDate getRptStartDate(int rptDurationMonths, LocalDate reportDate) {
		LocalDate rptStartDate = null;
		int startMonth = (reportDate.getMonthValue() + 12 - (rptDurationMonths-1)) % 12;
		// default year to same year as report date
		int startYear = reportDate.getYear();
		// Adjust report start year if report is duration >= reportMonth
		if ( rptDurationMonths >= reportDate.getMonthValue()) {
			startYear--;
		}
		rptStartDate = LocalDate.of(startYear, startMonth, 1);
		return rptStartDate;
	}
	
	protected LocalDate getRptEndDate(LocalDate reportDate) {
		
		LocalDate endOfMonth = reportDate.with(TemporalAdjusters.lastDayOfMonth());
		return endOfMonth;
	}

	
}
