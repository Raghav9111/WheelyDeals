package com.wheelyDeals.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wheelyDeals.entities.Customer;
import com.wheelyDeals.entities.ServiceProvider;
import com.wheelyDeals.model.ServiceProviderRegistrationModel;
import com.wheelyDeals.repositories.ServiceProviderRepo;
import com.wheelyDeals.utils.ApiResponse;

@Service
public class ServiceProviderService 
{
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private MailService  mailService;
	
	@Autowired
	private ServiceProviderRepo serviceRepo;

	public ApiResponse saveProvider(ServiceProviderRegistrationModel model) 
	{
		ApiResponse response = null;
		try {
			
			LocalDate date = LocalDate.now(); 
			ServiceProvider provider = new ServiceProvider(model.getEmail(),encoder.encode(model.getPassword()),"ROLE_SERVICEPROVIDER",false,date,false, model.getServiceProviderName(),model.getServiceProviderAddress(), model.getServiceProviderMobile()
					, model.getServiceProviderType(), model.getRating());
			if(provider != null) {
				provider = serviceRepo.save(provider);
				mailService.verificationMail(model.getEmail(),model.getServiceProviderName());
				response = new ApiResponse(true, "Service Provider Saved !");
			}/*
			else {
				response = new ApiResponse(false, "Service Provider not Saved !");
			}*/
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
			response = new ApiResponse(false, "Service Provider Save Failed !", ex.getMessage());
		}		
		return response;
	}
}

