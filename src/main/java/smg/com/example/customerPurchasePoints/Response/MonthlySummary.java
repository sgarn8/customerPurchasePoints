package smg.com.example.customerPurchasePoints.Response;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MonthlySummary {
	Integer yearOfPurchase;
	String monthAbeviation;
	Integer totalPoints;
	Double totalPurchaseAmt;

	public MonthlySummary(LocalDate purchDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM");
		this.setYearOfPurchase(purchDate.getYear());
		this.setMonthAbeviation(purchDate.format(formatter));
		this.setPoints(0);
		this.setPurchaseTotal(0D);	
	}
	public Integer getYearOfPurchase() {
		return yearOfPurchase;
	}
	public void setYearOfPurchase(Integer yearOfPurchase) {
		this.yearOfPurchase = yearOfPurchase;
	}
	
	public String getMonthAbeviation() {
		return monthAbeviation;
	}
	public void setMonthAbeviation(String monthAbeviation) {
		this.monthAbeviation = monthAbeviation;
	}
	public Integer getPoints() {
		return totalPoints;
	}
	public void setPoints(Integer points) {
		this.totalPoints = points;
	}
	public Double getPurchaseTotal() {
		return totalPurchaseAmt;
	}
	public void setPurchaseTotal(Double purchaseTotal) {
		this.totalPurchaseAmt = purchaseTotal;
	}
	
	@Override
	public String toString() {
		return String.format(
				"MonthlySummary[monthAbeviation='%s', points='%d', PurchaseTotal='%f']",
				this.getMonthAbeviation(), this.getPoints(), this.getPurchaseTotal() );
	}	
}
