package smg.com.example.customerPurchasePoints.Response;

import java.util.List;
import java.util.Map;

public class CustomerReport {
	String title;
	String startDate;
	String endDate;
	// Customer Summaries keyed by CustomerId
	Map<Long, CustomerPurchaseSummary> customerPurchaseSummaries;
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Map<Long, CustomerPurchaseSummary> getCustomerPurchaseSummaries() {
		return customerPurchaseSummaries;
	}

	public void setCustomerPurchaseSummaries(Map<Long, CustomerPurchaseSummary> customerPurchaseSummaries) {
		this.customerPurchaseSummaries = customerPurchaseSummaries;
	}

	public CustomerReport(String title) {
		this.title = title;
	}
}
