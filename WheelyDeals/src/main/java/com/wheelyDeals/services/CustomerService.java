package com.wheelyDeals.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wheelyDeals.entities.Customer;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.model.CustomerRegistrationModel;
import com.wheelyDeals.repositories.CustomerRepo;
import com.wheelyDeals.utils.ApiResponse;

@Service
public class CustomerService 
{
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private CustomerRepo custRepo;
	
	
	@Autowired
	private CustomerService custService;
	
	@Autowired
	private UserService userService;

	public ApiResponse saveCust(CustomerRegistrationModel cusmodel) {
		ApiResponse response = null;
		try {
			
			User user = userService.saveUser(cusmodel.getEmail(),encoder.encode(cusmodel.getPassword()),"ROLE_CUSTOMER",false);
			
			Customer customer = new Customer(cusmodel.getCustomerName(),cusmodel.getMobile());
			customer.setUserId(user.getUserId());
			customer.setActiveStatus(user.getActiveStatus());
			
			custRepo.save(customer);
			
			
			response = new ApiResponse(true, "Customer Saved !");
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
			response = new ApiResponse(false, "Customer Save Failed !", ex.getMessage());
		}		
		return response;
	}

}
