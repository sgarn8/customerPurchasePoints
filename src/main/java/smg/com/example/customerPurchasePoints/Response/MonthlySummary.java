package smg.com.example.customerPurchasePoints.Response;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	
	@Override
	public String toString() {
		return String.format(
				"MonthlySummary[monthAbeviation='%s', points='%d', PurchaseTotal='%f']",
				this.getMonthAbeviation(), this.getPoints(), this.getPurchaseTotal() );
	}

	
}
