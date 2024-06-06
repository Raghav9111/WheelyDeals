package com.wheelyDeals.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wheelyDeals.entities.Customer;
import com.wheelyDeals.entities.VehicleMaster;
import com.wheelyDeals.model.AddVehicleMasterModel;
import com.wheelyDeals.repositories.VehicleMasterRepo;
import com.wheelyDeals.utils.ApiResponse;

@Service
public class VehicleMasterService 
{
	@Autowired
	private VehicleMasterRepo vmRepo;

	
	public ApiResponse addVehicle(AddVehicleMasterModel model) {
        ApiResponse response = null;
		
		try {  
			VehicleMaster vm = new VehicleMaster(model.getVehicleModel(), model.getVehicleType(), model.getSeats(), model.getVehicleCapacity(), model.getVehicleImage());
			if(vm != null) {
				vm = vmRepo.save(vm);
				response = new ApiResponse(true, "Vehicle Added !");
			}
			else {
				response = new ApiResponse(false, "Vehicle not added !");
			}
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
			response = new ApiResponse(false, "Vehicle Save Failed !", ex.getMessage());
		}		
		return response;
	}


	public List<VehicleMaster> viewAll() {
		return vmRepo.findAll();
	}

}
