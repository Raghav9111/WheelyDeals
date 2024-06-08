package com.wheelyDeals.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wheelyDeals.entities.ServiceProviderVehicle;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.model.AddSPVehicleModel;
import com.wheelyDeals.model.AddVehicleChargesModel;
import com.wheelyDeals.services.SPVehicleService;
import com.wheelyDeals.services.ServiceProviderChargesService;
import com.wheelyDeals.utils.ApiResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/serviceProvider")
@RestController
public class ServiceProviderController extends BaseController
{
	@Autowired
	private ServiceProviderChargesService chargesService;
	
	@Autowired
	private SPVehicleService spVehicleService;
	
	@PostMapping("/addCharges")
	public ResponseEntity<ApiResponse> addCharges(@RequestBody AddVehicleChargesModel model) {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User)obj;
		ApiResponse response = chargesService.addVehicleCharges(model, user);
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@PostMapping("/addVehicle")
	public ResponseEntity<ApiResponse> addVehicle(AddSPVehicleModel model,MultipartFile file) {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User)obj;
		ApiResponse response = spVehicleService.addVehicle(model, user,file);
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@GetMapping("/viewVehicles")
	public ResponseEntity<ApiResponse> viewVehicles() {
		ApiResponse response;
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User)obj;
		try {
			response = spVehicleService.viewAll(user);
			return ResponseEntity.status(200).body(response);
		}catch (Exception e) {
			response = new ApiResponse(false, "Vehicles List Failed");
			return ResponseEntity.status(500).body(response);
		}
	}
	
}
