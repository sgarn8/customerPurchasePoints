package smg.com.example.customerPurchasePoints.Response;

import java.util.List;

public class CustomerReport {
	String title;
	String startDate;
	String endDate;
	List<CustomerPurchaseSummary> customerPurchaseSummaries;
	
	
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

	public List<CustomerPurchaseSummary> getCustomerPurchaseSummaries() {
		return customerPurchaseSummaries;
	}

	public void setCustomerPurchaseSummaries(List<CustomerPurchaseSummary> customerPurchaseSummaries) {
		this.customerPurchaseSummaries = customerPurchaseSummaries;
	}

	public CustomerReport(String title) {
		this.title = title;
	}
}
