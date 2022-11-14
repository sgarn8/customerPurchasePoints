package smg.com.example.customerPurchasePoints.Contoller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import smg.com.example.customerPurchasePoints.Entity.CustomerActivity;
import smg.com.example.customerPurchasePoints.Entity.CustomerPurchase;
import smg.com.example.customerPurchasePoints.Repository.CustomerPurchaseRepository;
import smg.com.example.customerPurchasePoints.Response.CustomerReport;
import smg.com.example.customerPurchasePoints.ServiceImpl.CustomerReportService;

@RestController
public class CustomerReportController {
	@Autowired
	CustomerReportService customerReportService;

	@Autowired
	CustomerPurchaseRepository customerPurchaseRepository;	
	
	@Value("${spring.application.name}")
	private String name;
	   
	@Value("${input.filename}")
	private String inputfilename;
	
	@GetMapping("/")
	public String index() {
		return "<h1>Customer Points App</h1>" +
				"<p><a href=http://localhost:8080/showinput>Display input File (json)</a></p>" +
				"<p><a href=http://localhost:8080/prev3MonthReport>Display 3 Month Report (json)</a></p>";
		
	}
	@GetMapping("/showinput")
	public String showinput() {
		System.out.println("Running " + name + " with input file: " + this.inputfilename);
		
		customerReportService.loadCustomerActivityFromFile(this.inputfilename);
		CustomerActivity customerActivity = new CustomerActivity();
		List<CustomerPurchase> customerPurchases = new ArrayList<CustomerPurchase>();
		for (CustomerPurchase cp : customerPurchaseRepository.findAll()) {
			customerPurchases.add(cp);
		}
		customerActivity.setCustomerPurchases(customerPurchases);
	    return customerActivity.toString();
	}

	@GetMapping("/prev3MonthReport")
	public String prev3MonthRpt() {
		// Assumption: report should be for the 3 months including the month of the reportDate.
		//
		// Ex:	reportDate="2021-04-15"
		//    	rptDurationMonths = 3
		//		startDate = 2021-02-01
		//		endDate = 2021-04-30
		// 
		int rptDurInMonths = 3;
		LocalDate reportDate = LocalDate.now();	
		CustomerReport customerReport = customerReportService.produceCustomerReport(this.inputfilename, reportDate, rptDurInMonths);
		Gson gson = new Gson();
		String output = gson.toJson(customerReport);
		return output;
		
//		Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
//		JsonElement je = JsonParser.parseString(output);
//		String prettyJsonString = gson2.toJson(je);
//		System.out.println(prettyJsonString);
//		return prettyJsonString;
	}
}
