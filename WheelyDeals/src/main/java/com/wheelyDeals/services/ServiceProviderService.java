package com.wheelyDeals.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wheelyDeals.entities.Booking;
import com.wheelyDeals.entities.ServiceProvider;
import com.wheelyDeals.entities.ServiceProviderVehicle;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.entities.VehicleRequest;
import com.wheelyDeals.model.ServiceProviderRegistrationModel;
import com.wheelyDeals.repositories.BookingRepo;
import com.wheelyDeals.repositories.SPVehicleRepo;
import com.wheelyDeals.repositories.ServiceProviderRepo;
import com.wheelyDeals.repositories.VehicleRequestRepo;
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
	
	@Autowired
	private VehicleRequestRepo vreqRepo;
	
	@Autowired
	private SPVehicleRepo spVehicleRepo;
	
	@Autowired
	private BookingRepo bookingRepo;

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

	public List<ServiceProvider> viewAll() {
		
		return serviceRepo.findAll();
	}

	public ApiResponse viewRequest() 
	{
		ApiResponse response = null;
		try 
		{
			List<VehicleRequest> spreqList = new ArrayList<>();
		
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = (User) principal;
			
			System.out.println(user);
			Optional<ServiceProvider> op = serviceRepo.findById(user.getUserId());
			if(op.isPresent())
			{
				ServiceProvider sp = op.get();
				List<VehicleRequest> vreqList =  vreqRepo.findAll();
				
				for(VehicleRequest list : vreqList)
				{
					
					Optional<List<ServiceProviderVehicle>>  opt  =  spVehicleRepo.findByServiceProviderAndFuelTypeAndVehicleMasterAndStatus(sp,list.getFuelType(),list.getVehicleMaster(),"Deleted");
					if(opt.isPresent())
					{
						List<ServiceProviderVehicle> spList = opt.get();
						if(spList.isEmpty())
						{
							System.out.println("ServiceProvider Vehicle Empty");
							
						}
						else
						{
							spreqList.add(list);
							
						}
					}
				}
				if(spreqList.isEmpty())
				{
					response = new ApiResponse(false,"No request Available");
				}
				else
				{
					response = new ApiResponse(true,"Request Available",spreqList);
				}
			}
			else
			{
				System.out.println("Service Provider fetch failed");
			}
			
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
			response = new ApiResponse(false, "Service Provider view vehicle failed !", ex.getMessage());
		}		
		return response;
	}

	public ApiResponse requestVehicles(Integer reqId) {
		/*List<ServiceProviderVehicle> resList = new ArrayList<>();
		List<VehicleRequest> spreqList = new ArrayList<>();
		List<ServiceProviderVehicle> spList = new ArrayList<>();
		
		try 
		{
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = (User) principal;
			System.out.println(user);
			Optional<ServiceProvider> op = serviceRepo.findById(user.getUserId());
			
			if(op.isPresent())
			{
				ServiceProvider sp = op.get();
				List<VehicleRequest> vreqList =  vreqRepo.findAll();
				

				for(VehicleRequest list : vreqList)
				{
					
					Optional<List<ServiceProviderVehicle>>  opt  =  spVehicleRepo.findByServiceProviderAndFuelTypeAndVehicleMasterAndStatus(sp,list.getFuelType(),list.getVehicleMaster(),"Deleted");
					if(opt.isPresent())
					{
						 spList = opt.get();
						if(spList.isEmpty())
						{
							System.out.println("ServiceProvider Vehicle Empty");
						}
						else
						{
							spreqList.add(list);
						}
					}
				}
				if(spreqList.isEmpty())
				{
					response = new ApiResponse(false,"No request Available");
				}
				else
				{					
					for(VehicleRequest vlist : spreqList)
					{
						Optional<List<Booking>> opt = bookingRepo.findByTripStartDateAndTripEndDate(vlist.getTripStartDate(),vlist.getTripEndDate());
						if(opt.isPresent())
						{
							List<Booking> listBooking = opt.get();
							System.out.println(listBooking);
							for(Booking blist : listBooking)
							{
								for(ServiceProviderVehicle spvlist : spList)
								{
									if(blist.getVehicleRequestResponse().getServiceProviderVehicle().getSpVehicleId().equals(spvlist.getSpVehicleId()))
									{
										continue;
									}
									else
									{
										resList.add(spvlist);
									}
								}
							}
						}
						else
						{
							response = new ApiResponse(true,"All Vehicles Available",spList);							
						}
						if(resList.isEmpty())
						{
							response = new ApiResponse(false,"Required Vehicle not available");
						}
						else
						{
							response = new ApiResponse(true,"Required Vehicles Available",resList);							
						}
					}
				}
			}	
		}
		catch(Exception ex) {
			System.err.println(ex.getMessage());
			response = new ApiResponse(false, "Service Provider view vehicle failed !", ex.getMessage());
		}*/
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = (User) principal;
			Optional<ServiceProvider> op = serviceRepo.findById(user.getUserId());
			Optional<VehicleRequest> objVreq = vreqRepo.findById(reqId);
			if(op.isPresent() && objVreq.isPresent()){
				ServiceProvider sp = op.get();
				VehicleRequest vreq =  objVreq.get();
				Optional<List<ServiceProviderVehicle>> spvObj = spVehicleRepo.findAvailableServiceProviderVehicles(sp, vreq.getVehicleMaster(), vreq.getTripEndDate(), vreq.getTripStartDate());
				
				if(spvObj.isPresent()) {
					return new ApiResponse(true, "Available Vehicles", spvObj.get());
				}
				else {
					return new ApiResponse(false, "Vehicles not available");
				}
			}
			else {
				return new ApiResponse(false, "Request or Service Provider not found!");
			}
		}catch (Exception e) {
			return new ApiResponse(false, "Vehicle Fetch Exception", e.getMessage());
		}
	}
}

