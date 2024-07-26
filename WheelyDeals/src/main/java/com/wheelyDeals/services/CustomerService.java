package com.wheelyDeals.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wheelyDeals.entities.Customer;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.entities.VehicleMaster;
import com.wheelyDeals.entities.VehicleRequest;
import com.wheelyDeals.model.BookingRequestModel;
import com.wheelyDeals.model.CustomerRegistrationModel;
import com.wheelyDeals.repositories.CustomerRepo;
import com.wheelyDeals.repositories.VehicleMasterRepo;
import com.wheelyDeals.repositories.VehicleRequestRepo;
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
	private VehicleMasterRepo vmastRepo;
	
	@Autowired
	private CustomerService custService;
	
	@Autowired
	private VehicleRequestRepo vreqRepo;	

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
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
			response = new ApiResponse(false, "Customer Save Failed !", ex.getMessage());
		}		
		return response;
	}

	public List<Customer> viewAll() {
			return custRepo.findAll();
			
	}

	public ApiResponse requestBooking(BookingRequestModel model) {
		
		ApiResponse response = null;
		Customer cust = custService.getCustomer();
		Optional<VehicleMaster> op  =  vmastRepo.findById(model.getVehicleMaster());
		
		try {
		if(op.isPresent()) 
		{
			VehicleMaster vmast = op.get();
			LocalDate date = LocalDate.now(); 
			VehicleRequest req = new VehicleRequest(cust,vmast, model.getSource(), model.getDestination(),date, model.getDescription(),model.getTripStartDate(),model.getTripEndDate(), model.getIsAc(), model.getFuelType(),"pending",model.getDistance());
			
			vreqRepo.save(req);	
			 response = new ApiResponse(true, "Request Saved");
		}
		}
		 catch (Exception e) {
			 
			 response = new ApiResponse(false, "Request Save Failed",e.getMessage());
		 }
		return response;
	}

	private Customer getCustomer() 
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User) principal;
		
		System.out.println(user);
		Optional<Customer> op = custRepo.findById(user.getUserId());
		
		return op.get();
	}
	
	

}
