package smg.com.example.customerPurchasePoints.ServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import smg.com.example.customerPurchasePoints.Entity.CustomerActivity;
import smg.com.example.customerPurchasePoints.Entity.CustomerPurchase;
import smg.com.example.customerPurchasePoints.Repository.CustomerPurchaseRepository;
import smg.com.example.customerPurchasePoints.Response.CustomerPurchaseSummary;
import smg.com.example.customerPurchasePoints.Response.CustomerReport;
import smg.com.example.customerPurchasePoints.Response.MonthlySummary;
import smg.com.example.customerPurchasePoints.mapper.CustomerActivityMapper;

@Service
public class CustomerReportService {
	@Autowired
	CustomerPurchaseRepository customerPurchaseRepository;
	
	public CustomerReport produceCustomerReport(String inputFilename, LocalDate reportDate, int rptDurInMonths) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String title = rptDurInMonths + " Month Report based on input file: " + inputFilename;
		CustomerReport customerReport = new CustomerReport(title);
			    
		LocalDate rptStartDate = getRptStartDate(rptDurInMonths, reportDate);
		LocalDate rptEndDate = getRptEndDate(reportDate);
		customerReport.setStartDate(rptStartDate.format(formatter));
		customerReport.setEndDate(rptEndDate.format(formatter));
		
		loadCustomerActivityFromFile(inputFilename);
		List<CustomerPurchaseSummary> customerPurchaseSummaries = summarizeCustomerPurchaseByMonth();
		customerReport.setCustomerPurchaseSummaries(customerPurchaseSummaries);

		return customerReport;
	}

	private List<CustomerPurchaseSummary> summarizeCustomerPurchaseByMonth() {
		List<CustomerPurchaseSummary> customerResponses=null;
		Map<Long, CustomerPurchaseSummary> customerSummaryMap = new HashMap<Long, CustomerPurchaseSummary>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM");
		CustomerPurchaseSummary customerPurchaseSummary = null;
		for (CustomerPurchase customerpurchase : customerPurchaseRepository.findAll()) {
			Long custId = customerpurchase.getCustomer().getId();
			LocalDate purchDate = customerpurchase.getPurchaseDate().toLocalDate();
			String monthAbrev = purchDate.format(formatter);
			
			if (customerSummaryMap.containsKey(custId)) {
				customerPurchaseSummary = customerSummaryMap.get(custId);
				// check if summary already contains month
				// otherwise add new month
			} else
			{
				customerPurchaseSummary = new CustomerPurchaseSummary();
				customerPurchaseSummary.setCustomerId(customerpurchase.getCustomer().getId());
				customerPurchaseSummary.setCustomerName("TBD");
				
				Map<String, MonthlySummary> custMonthlySummaries = new HashMap<>();
				
				MonthlySummary monthlySummary = new MonthlySummary();
				monthlySummary.setMonthAbeviation(purchDate.format(formatter));
				monthlySummary.setPoints(0);
				monthlySummary.setPurchaseTotal(0D);				
				custMonthlySummaries.put(monthAbrev, monthlySummary);
				
				customerPurchaseSummary.setMonthlySummaries(custMonthlySummaries);;
				
			}
			if (customerpurchase.getPurchaseAmt()>200) {
				
			}
			//System.out.println(customerpurchase.toString());
			
		}
		return customerResponses;
	}
	
	public void loadCustomerActivityFromFile(String inputFilename) {
		CustomerActivity customerActivity;
		CustomerActivityMapper customerActivityMapper = new CustomerActivityMapper(inputFilename);
	    customerActivity = customerActivityMapper.getCustomerActivity();
	    customerActivity.updatePurchMonthAndDate();
	    
	    for (CustomerPurchase customerPurchase  : customerActivity.getCustomerPurchases()) {
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
