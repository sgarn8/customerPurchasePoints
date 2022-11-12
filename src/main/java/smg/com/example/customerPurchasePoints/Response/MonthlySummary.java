package smg.com.example.customerPurchasePoints.Response;

public class MonthlySummary {
	String monthAbeviation;
	Integer points;
	Double purchaseTotal;
	
	public String getMonthAbeviation() {
		return monthAbeviation;
	}
	public void setMonthAbeviation(String monthAbeviation) {
		this.monthAbeviation = monthAbeviation;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Double getPurchaseTotal() {
		return purchaseTotal;
	}
	public void setPurchaseTotal(Double purchaseTotal) {
		this.purchaseTotal = purchaseTotal;
	}
	
}
