package smg.com.example.customerPurchasePoints.Response;

import java.util.List;
import java.util.Map;

public class CustomerPurchaseSummary {
	Long customerId;
	String customerName;
	Map<String, MonthlySummary> monthlySummaries;
	Double totalPoints;
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Double getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(Double totalPoints) {
		this.totalPoints = totalPoints;
	}
	public Map<String, MonthlySummary> getMonthlySummaries() {
		return monthlySummaries;
	}
	public void setMonthlySummaries(Map<String, MonthlySummary> monthlySummaries) {
		this.monthlySummaries = monthlySummaries;
	}

}
