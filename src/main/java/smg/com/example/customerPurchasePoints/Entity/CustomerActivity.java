package smg.com.example.customerPurchasePoints.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.google.gson.Gson;

@Entity
public class CustomerActivity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "cust_purch_id")
	public List<CustomerPurchase> customerPurchases;
	
	public void updatePurchMonthAndDate() {
		for (CustomerPurchase cp : this.customerPurchases) {
			cp.updatePurchMonthAndYear();
		}
	}
	
	public List<CustomerPurchase> getCustomerPurchases() {
		return customerPurchases;
	}

	public void setCustomerPurchases(List<CustomerPurchase> customerPurchases) {
		this.customerPurchases = customerPurchases;
	}

	public String toString() {
		String output="";

		Gson gson = new Gson();
		output = gson.toJson(this);
		return output;
	}
}
