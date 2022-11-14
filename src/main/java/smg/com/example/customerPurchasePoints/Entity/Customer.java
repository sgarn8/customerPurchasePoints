package smg.com.example.customerPurchasePoints.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.boot.model.source.spi.Sortable;


@Entity
public class Customer implements Sortable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    @Column(name = "first_name", nullable = false)
	private String firstName;
    
    @Column(name = "last_name", nullable = false)
	private String lastName;

	public Customer() {}
	
    public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		String retString = this.lastName;
		if (this.firstName.length()>0) {
			retString += ", " + firstName;
		}
		return retString;
	}

	@Override
	public String toString() {
		return String.format(
				"Customer[id=%d, firstName='%s', lastName='%s']",
				id, firstName, lastName);
	}

	@Override
	public boolean isSorted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getComparatorName() {
		// TODO Auto-generated method stub
		return null;
	}



}
