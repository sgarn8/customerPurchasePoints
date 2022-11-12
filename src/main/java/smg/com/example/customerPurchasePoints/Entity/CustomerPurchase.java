package smg.com.example.customerPurchasePoints.Entity;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CustomerPurchase {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne (cascade = {CascadeType.ALL})
	@JoinColumn(name = "cust_activity_id")
	private CustomerActivity customerActivity;

	@ManyToOne (cascade = {CascadeType.ALL})
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	public Date purchaseDate;
	public double purchaseAmt;
	public int purchaseMonth;
	public int purchaseYear;
	
	protected CustomerPurchase() {}

	public CustomerActivity getCustomerActivity() {
		return customerActivity;
	}

	public void setCustomerActivity(CustomerActivity customerActivity) {
		this.customerActivity = customerActivity;
	}

	public CustomerPurchase(Customer customer, Date purchaseDate, double purchaseAmt) {
		this.customer = customer;
		this.purchaseDate = purchaseDate;
		updatePurchMonthAndYear();
		this.purchaseAmt = purchaseAmt;
		
	}

	@Override
	public String toString() {
		LocalDate purchaseDateLD = this.purchaseDate.toLocalDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DecimalFormat decFormatter = new DecimalFormat("$,###,##0.00");
		return String.format(
				"Customer Purchase[id=%d, CustomerName='%s', PurchaseDate='%s', PurchaseAmount='%s']",
				id, customer.getName(), purchaseDateLD.format(formatter), decFormatter.format(this.purchaseAmt) );
	}

	public Long getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
		updatePurchMonthAndYear();
	}

	public double getPurchaseAmt() {
		return purchaseAmt;
	}

	public void setPurchaseAmount(double purchaseAmt) {
		this.purchaseAmt = purchaseAmt;
	}	

	public int getPurchaseMonth() {
		return purchaseMonth;
	}

	public void setPurchaseMonth(int purchaseMonth) {
		this.purchaseMonth = purchaseMonth;
	}

	public int getPurchaseYear() {
		return purchaseYear;
	}

	public void setPurchaseYear(int purchaseYear) {
		this.purchaseYear = purchaseYear;
	}

	public void setPurchaseAmt(double purchaseAmt) {
		this.purchaseAmt = purchaseAmt;
	}

	public void updatePurchMonthAndYear() {
		LocalDate purchaseDateLD = this.purchaseDate.toLocalDate();
		this.setPurchaseMonth(purchaseDateLD.getMonthValue()+1);
		this.setPurchaseYear(purchaseDateLD.getYear());

	}
}
