package com.wheelyDeals.services;

import java.util.List;
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
			System.out.println(spobj.get());
			Optional<VehicleMaster> vmobj = vmRepo.findById(model.getVehicleMasterId());
			System.out.println(vmobj.get());
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

	public ApiResponse updateCharges(Integer chargesId, AddVehicleChargesModel model) {
		try {
			Optional<ServiceProviderCharges> obj = spvChargesRepo.findById(chargesId);
			if(obj.isPresent()) {
				ServiceProviderCharges spvCharges = obj.get();
				spvCharges.updateCharges(model);
				spvChargesRepo.save(spvCharges);
				return new ApiResponse(true, "Charges Update Successfully", spvCharges);
			}
			else {
				return new ApiResponse(false, "Master Vehicle charges not found");
			}
		}catch (Exception e) {
			return new ApiResponse(false, "Charges Update Failed", e.getMessage());
		}
	}

	public ApiResponse viewVehicleCharges(Object obj, Integer vehicleMasterId) {
		try {
			User user = (User)obj;
			Optional<ServiceProvider> ob = serviceProviderRepo.findById(user.getUserId());
			Optional<VehicleMaster> vmobj = vmRepo.findById(vehicleMasterId);
			if(ob.isPresent() && vmobj.isPresent()) {
				ServiceProvider sp = ob.get();
				VehicleMaster vm = vmobj.get();
				Optional<ServiceProviderCharges> spCharges = spvChargesRepo.findByVehicleMasterAndServiceProvider(vm, sp);
				if(spCharges.isPresent()) {
					return new ApiResponse(true, vm.getVehicleModel()+" Charges",spCharges.get());
				}
				else {
					return new ApiResponse(false, "Charges not present");
				}
			}
			else {
				return new ApiResponse(false, "Vehicle Master or Service Provider not found");
			}
		}
		catch (Exception e) {
			return new ApiResponse(false, "View Charges Failed", e.getMessage());
		}
	}

}
