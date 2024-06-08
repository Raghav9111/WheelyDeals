package com.wheelyDeals.services;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wheelyDeals.entities.ServiceProvider;
import com.wheelyDeals.entities.ServiceProviderCharges;
import com.wheelyDeals.entities.ServiceProviderVehicle;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.entities.VehicleMaster;
import com.wheelyDeals.model.AddSPVehicleModel;
import com.wheelyDeals.repositories.SPVehicleRepo;
import com.wheelyDeals.repositories.ServiceProviderRepo;
import com.wheelyDeals.repositories.VehicleMasterRepo;
import com.wheelyDeals.utils.ApiResponse;

@Service
public class SPVehicleService {
	
	@Autowired
	private ServiceProviderRepo serviceProviderRepo;

	@Autowired
	private VehicleMasterRepo vmRepo;
	
	@Autowired
	private SPVehicleRepo spVehicleRepo;

	
	public ApiResponse addVehicle(AddSPVehicleModel model,User user,MultipartFile file) {
		try {
			
			byte arr[] = file.getBytes();
			
			String fileName = file.getOriginalFilename();		
			String extension = fileName.substring(fileName.lastIndexOf("."));
			String uploadFile =  UUID.randomUUID().toString()+extension;	
			File fileObj = new File("E:\\WheelyDeals\\VehicleRCCard", uploadFile);
			FileOutputStream fos = new FileOutputStream(fileObj);
			fos.write(arr);
			fos.flush();
			fos.close();		
			String filePath =  fileObj.getAbsolutePath();
			model.setRcCard(filePath);
			Optional<ServiceProvider> spobj= serviceProviderRepo.findById(user.getUserId());
			Optional<VehicleMaster> vmobj = vmRepo.findById(model.getVehicleMasterId());
			if(spobj.isPresent() && vmobj.isPresent()) {
				
				ServiceProvider serviceProvider = spobj.get();
				VehicleMaster vehicleMaster = vmobj.get();
				
				ServiceProviderVehicle spVehicle = new ServiceProviderVehicle(serviceProvider, vehicleMaster, model.getRegistrationNumber(), 
						model.getRcCard(), model.getFuelType(), "Not Available");
				
				spVehicle = spVehicleRepo.save(spVehicle);
				return new ApiResponse(true, "Vehicle Saved successfully");	
			}
			else {
				return new ApiResponse(false, "Service Provider or Vehicle master not available");	
			}
			
		}catch (Exception e) {
			return new ApiResponse(false, "Add Vehicle Failed", e.getMessage());
		}
	}


	public ApiResponse viewAll(User user) 
	{
		try
		{
			Optional<ServiceProvider> spobj= serviceProviderRepo.findById(user.getUserId());
			if(spobj.isPresent())
			{
				ServiceProvider sp = spobj.get();
				Optional<List<ServiceProviderVehicle>> list = spVehicleRepo.findByStatusAndServiceProvider("Deleted", sp );
				if(list.isPresent())
					return new ApiResponse(true, "Vehicle list", list.get());
				
				else
					return new ApiResponse(false, "Vehicle list not present");	
			}
			else
			{
				return new ApiResponse(false, "Service provider not found");
			}
		}
		catch (Exception e) {
			return new ApiResponse(false, "Vehicle list failed", e.getMessage());
		}
	}


}
