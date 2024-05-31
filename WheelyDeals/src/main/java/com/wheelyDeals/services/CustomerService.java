package com.wheelyDeals.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
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
	private MailService mailService;
	
	@Autowired
	private CustomerService custService;
	
	@Autowired
	private UserService userService;

	public ApiResponse saveCust(CustomerRegistrationModel cusmodel) {
		ApiResponse response = null;
		
		try {
			LocalDate date = LocalDate.now();     
			Customer customer = new Customer(cusmodel.getEmail(), encoder.encode( cusmodel.getPassword()), "ROLE_CUSTOMER", false,date,false, cusmodel.getCustomerName(), cusmodel.getMobile());
			if(customer != null) {
				customer = custRepo.save(customer);
				mailService.verificationMail(cusmodel.getEmail(),cusmodel.getCustomerName());
				response = new ApiResponse(true, "Customer Saved !");
			}
			else {
				response = new ApiResponse(false, "Customer not Saved !");
			}
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
			response = new ApiResponse(false, "Customer Save Failed !", ex.getMessage());
		}		
		return response;
	}

	public List<Customer> viewAll() {
			return custRepo.findAll();
			
	}

}
