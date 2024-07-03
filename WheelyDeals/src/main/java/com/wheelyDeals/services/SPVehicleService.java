package com.wheelyDeals.services;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wheelyDeals.entities.ServiceProvider;
import com.wheelyDeals.entities.ServiceProviderCharges;
import com.wheelyDeals.entities.ServiceProviderVehicle;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.entities.VehicleMaster;
import com.wheelyDeals.model.AddSPVehicleModel;
import com.wheelyDeals.repositories.SPVehicleChargesRepo;
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
	
	@Autowired
	private SPVehicleChargesRepo spVehicleChargesRepo;

	
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
				Optional<ServiceProviderCharges> spcobj = spVehicleChargesRepo.findByVehicleMasterAndServiceProvider(vehicleMaster,serviceProvider);
				System.err.println("hjhjjgjgjgjjg");
				if(spcobj.isPresent()) {
					ServiceProviderCharges spc = spcobj.get();
					ServiceProviderVehicle spVehicle = new ServiceProviderVehicle();
					
					Boolean fuelStatus = spVehicle.checkFuelStatus(spc,model);
					System.err.println(fuelStatus);
					if(fuelStatus == true) {
						spVehicle = new ServiceProviderVehicle(serviceProvider, vehicleMaster, model.getRegistrationNumber(), 
								model.getRcCard(), model.getFuelType(), "Not Available");
						
						spVehicle = spVehicleRepo.save(spVehicle);
						return new ApiResponse(true, "Vehicle Saved successfully",spVehicle);
					}
					else {
						return new ApiResponse(false, "Charges Not Defined");
					}
				}
				else {
					return new ApiResponse(false, "Charges Not Defined");
				}
					
			}
			else {
				return new ApiResponse(false, "Service Provider or Vehicle master not available");	
			}
			
		}catch (Exception e) {
			return new ApiResponse(false, "Add Vehicle Failed", e.getMessage());
		}
	}


	public ApiResponse viewAll(User user, int page, int size) 
	{
		try
		{
			Optional<ServiceProvider> spobj= serviceProviderRepo.findById(user.getUserId());
			if(spobj.isPresent())
			{
				ServiceProvider sp = spobj.get();
				Optional<List<ServiceProviderVehicle>> list = spVehicleRepo.findByStatusAndServiceProvider("Deleted", sp,PageRequest.of(page-1,size) );
				Long count = spVehicleRepo.countByStatus("Deleted").get();
				
				HashMap<String, Object> hm = new HashMap<>();
				hm.put("list", list.get());
				hm.put("count", count);
				
				if(list.isPresent())
					return new ApiResponse(true, "Vehicle list", hm);
				
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


	public ApiResponse deleteVehicle(Integer vid) {
		try {
			Optional<ServiceProviderVehicle> obj = spVehicleRepo.findById(vid);
			if(obj.isPresent()) {
				ServiceProviderVehicle spVehicle = obj.get();
				spVehicle.setStatus("Deleted");
				spVehicleRepo.save(spVehicle);
				return new ApiResponse(true, "Vehicle Deleted Successfully");
			}
			else {
				return new ApiResponse(false, "Vehicle not found");
			}
		}
		catch (Exception e) {
			return new ApiResponse(false, "Vehicle Delete Failed", e.getMessage());
		}
	}
}
