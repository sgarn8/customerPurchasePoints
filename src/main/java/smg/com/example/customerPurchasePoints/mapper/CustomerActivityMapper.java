package smg.com.example.customerPurchasePoints.mapper;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import smg.com.example.customerPurchasePoints.Entity.CustomerActivity;

public class CustomerActivityMapper {
	private CustomerActivity customerActivity;
	
	public CustomerActivityMapper(String inputFile) {
		// create Object Mapper
//		ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JSR310Module());
//        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
//
//	
//		// read JSON file and map/convert to java POJO
//		try {
//		    customerActivity = mapper.readValue(new File(inputFile), CustomerActivity.class);
//		    
//		} catch (IOException e) {
//		    e.printStackTrace();
//		}
		try {
		    // create Gson instance
		    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		    // create a reader
		    Reader reader = Files.newBufferedReader(Paths.get(inputFile));
		    customerActivity = gson.fromJson(reader, CustomerActivity.class);

		    // close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}	}
	
	public CustomerActivity getCustomerActivity() {
		return customerActivity;
	}
	
}
