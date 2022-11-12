package smg.com.example.customerPurchasePoints.ServiceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public CustomerReport produceCustomerReport(String inputFilename, LocalDate reportDate, int rptDurInMonths) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String title = rptDurInMonths + " Month Report based on input file: " + inputFilename;
		CustomerReport customerReport = new CustomerReport(title);
			    
		LocalDate rptStartDate = getRptStartDate(rptDurInMonths, reportDate);
		LocalDate rptEndDate = getRptEndDate(reportDate);
		customerReport.setStartDate(rptStartDate.format(formatter));
		customerReport.setEndDate(rptEndDate.format(formatter));
		
		loadCustomerActivityFromFile(inputFilename);
		List<CustomerPurchaseSummary> customerPurchaseSummaries = summarizeCustomerPurchasesByMonth(
				rptStartDate, rptEndDate);
		customerReport.setCustomerPurchaseSummaries(customerPurchaseSummaries);
		
		return customerReport;
	}

	private List<CustomerPurchaseSummary> summarizeCustomerPurchasesByMonth(
			LocalDate startDate, LocalDate endDate) {
		List<CustomerPurchaseSummary> customerPurchaseSummaries=null;
		Map<Long, CustomerPurchaseSummary> customerSummaryMap = new HashMap<Long, CustomerPurchaseSummary>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM");
		CustomerPurchaseSummary customerPurchaseSummary = null;
		Map<String, MonthlySummary> custMonthlySummariesMap = null;
		MonthlySummary monthlySummary=null;
//		for (CustomerPurchase customerpurchase : customerPurchaseRepository.findAll()) {
		for (CustomerPurchase customerpurchase : customerPurchaseRepository.findAllByPurchaseDateBetween(
				Date.valueOf(startDate), Date.valueOf(endDate))) {
			
			Long custId = customerpurchase.getCustomer().getId();
			LocalDate purchDate = customerpurchase.getPurchaseDate().toLocalDate();
			String monthAbrev = purchDate.format(formatter);
			Optional<Customer> customer = customerRepository.findById(custId);
			
			if (customerSummaryMap.containsKey(custId)) {
				customerPurchaseSummary = customerSummaryMap.get(custId);
				custMonthlySummariesMap = customerPurchaseSummary.getMonthlySummaries();
				
				// check if summary already contains month
				if ( custMonthlySummariesMap != null && custMonthlySummariesMap.containsKey(monthAbrev)) {
					monthlySummary = custMonthlySummariesMap.get(monthAbrev);
				} else {
				// otherwise add new month
					monthlySummary = new MonthlySummary();
					monthlySummary.setMonthAbeviation(purchDate.format(formatter));
					monthlySummary.setPoints(0);
					monthlySummary.setPurchaseTotal(0D);				
					custMonthlySummariesMap.put(monthAbrev, monthlySummary);					
				}
			} else {
				customerPurchaseSummary = new CustomerPurchaseSummary();
				
				customerPurchaseSummary.setCustomerId(customerpurchase.getCustomer().getId());
				customerPurchaseSummary.setCustomerName("TBD");
				
				custMonthlySummariesMap = new HashMap<>();
				
				monthlySummary = new MonthlySummary();
				monthlySummary.setMonthAbeviation(purchDate.format(formatter));
				monthlySummary.setPoints(0);
				monthlySummary.setPurchaseTotal(0D);				
				custMonthlySummariesMap.put(monthAbrev, monthlySummary);
				
				customerPurchaseSummary.setMonthlySummaries(custMonthlySummariesMap);;
				
			}
			int pointsToAdd=0;
			if (customerpurchase.getPurchaseAmt()>100) {
				pointsToAdd = (int) ((customerpurchase.getPurchaseAmt() - 100D) * 2D + 50);
				monthlySummary.setPoints(monthlySummary.getPoints() + pointsToAdd);
			} else if (customerpurchase.getPurchaseAmt()>50) {
				pointsToAdd = (int) (customerpurchase.getPurchaseAmt() - 50D);
				monthlySummary.setPoints(monthlySummary.getPoints() + pointsToAdd);
			}
			monthlySummary.setPurchaseTotal(monthlySummary.getPurchaseTotal() + customerpurchase.getPurchaseAmt());
//			System.out.println(customerpurchase.toString());
//			System.out.println(monthlySummary.toString());
			customerPurchaseSummary.setCustomerName(
					customer.get().getFirstName() + " " + customer.get().getLastName() );
			customerSummaryMap.put(custId, customerPurchaseSummary);

			
		}
		customerPurchaseSummaries = customerSummaryMap.values().stream()
				.collect(Collectors.toList());
		return customerPurchaseSummaries;
	}
	
	public void loadCustomerActivityFromFile(String inputFilename) {
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
