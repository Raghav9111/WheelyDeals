package com.wheelyDeals.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wheelyDeals.entities.ServiceProviderVehicle;
import com.wheelyDeals.entities.VehicleImages;
import com.wheelyDeals.repositories.SPVehicleRepo;
import com.wheelyDeals.repositories.VehicleImageRepo;
import com.wheelyDeals.utils.ApiResponse;

@Service
public class VehicleImageService {
	@Autowired
	private VehicleImageRepo viRepo;
	
	@Autowired
	private SPVehicleRepo spvRepo;

	public ApiResponse uploadImages(Integer vid, ArrayList<MultipartFile> files) throws IOException {
		try {
			Optional<ServiceProviderVehicle> obj = spvRepo.findById(vid);
			ArrayList<String> filePath =  new ArrayList<>();
			if(obj.isPresent()) {
				ServiceProviderVehicle spv = obj.get();
				for(MultipartFile mpf : files) {
					byte arr[] = mpf.getBytes();
					
					String fileName = mpf.getOriginalFilename();		
					String extension = fileName.substring(fileName.lastIndexOf("."));
					String uploadFile =  UUID.randomUUID().toString()+extension;	
					File fileObj = new File("E:\\WheelyDeals\\SPVehicleImages", uploadFile);
					FileOutputStream fos = new FileOutputStream(fileObj);
					fos.write(arr);
					fos.flush();
					fos.close();		
					
					filePath.add(fileObj.getAbsolutePath());					
				}
				if(filePath.isEmpty()) {
					return new ApiResponse(false, "Images not found");
				}
				else {
					VehicleImages vImg = new VehicleImages(filePath.get(0), filePath.get(1), filePath.get(2), filePath.get(3), spv);
					viRepo.save(vImg);
					return new ApiResponse(true, "Vehicle Images Saved Successfully");
				}
			}
			else {
				return new ApiResponse(false, "Service Provider Vehicle not found");
			}
		}
		catch (Exception e) {
			return new ApiResponse(false, "Vehicle Images upload failed", e.getMessage());
		}
	}
	
	
}
