package com.wheelyDeals.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wheelyDeals.entities.ServiceProvider;
import com.wheelyDeals.entities.ServiceProviderCharges;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.entities.VehicleMaster;
import com.wheelyDeals.model.AddVehicleChargesModel;
import com.wheelyDeals.repositories.SPVehicleChargesRepo;
import com.wheelyDeals.repositories.ServiceProviderRepo;
import com.wheelyDeals.repositories.VehicleMasterRepo;
import com.wheelyDeals.utils.ApiResponse;

@Service
public class ServiceProviderChargesService {
	
	@Autowired
	private ServiceProviderRepo serviceProviderRepo;
	
	@Autowired
	private SPVehicleChargesRepo spvChargesRepo;
	
	@Autowired
	private VehicleMasterRepo vmRepo;

	public ApiResponse addVehicleCharges(AddVehicleChargesModel model,User user) {
		try {
			Optional<ServiceProvider> spobj= serviceProviderRepo.findById(user.getUserId());
			Optional<VehicleMaster> vmobj = vmRepo.findById(model.getVehicleMasterId());
			if(spobj.isPresent() && vmobj.isPresent()) {
				
				ServiceProvider serviceProvider = spobj.get();
				VehicleMaster vehicleMaster = vmobj.get();
				
				ServiceProviderCharges spCharges = new ServiceProviderCharges(serviceProvider, vehicleMaster, model.getMinPerDayKm(), 
						model.getPetrolRatePerKm(), model.getDieselRatePerKm(), model.getCngRatePerKm(), model.getEvRatePerKm(), model.getAcRatePerKm());
				
				spCharges = spvChargesRepo.save(spCharges);
				return new ApiResponse(true, "Charges Saved successfully");	
			}
			else {
				return new ApiResponse(false, "Service Provider or Vehicle master not available");	
			}
			
		}catch (Exception e) {
			return new ApiResponse(false, "Add Charges Failed", e.getMessage());
		}
	}

}
