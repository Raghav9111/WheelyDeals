package com.wheelyDeals.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.model.AddSPVehicleModel;
import com.wheelyDeals.model.AddVehicleChargesModel;
import com.wheelyDeals.services.SPVehicleService;
import com.wheelyDeals.services.ServiceProviderChargesService;
import com.wheelyDeals.services.ServiceProviderService;
import com.wheelyDeals.services.VehicleImageService;
import com.wheelyDeals.utils.ApiResponse;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/serviceProvider")
@RestController
@CrossOrigin
public class ServiceProviderController extends BaseController
{
	@Autowired
	private ServiceProviderChargesService chargesService;
	
	@Autowired
	private SPVehicleService spVehicleService;
	
	@Autowired
	private VehicleImageService viService;
	
	@Autowired
	private ServiceProviderService spService;
	
	@PostMapping("/addCharges")
	public ResponseEntity<ApiResponse> addCharges(@RequestBody AddVehicleChargesModel model) {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User)obj;
		System.out.println(model);
		System.out.println(user);
		ApiResponse response = chargesService.addVehicleCharges(model, user);
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(200).body(response);
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
			return ResponseEntity.status(200).body(response);
		}
	}
	
	@GetMapping("/viewVehicles")
	public ResponseEntity<ApiResponse> viewVehicles(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size) {
		ApiResponse response;
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User)obj;
		try {
			response = spVehicleService.viewAll(user,page,size);
			return ResponseEntity.status(200).body(response);
		}catch (Exception e) {
			response = new ApiResponse(false, "Vehicles List Failed");
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@DeleteMapping("/deleteVehicle/{vid}")
	public ResponseEntity<ApiResponse> deleteVehicle(@PathVariable(name="vid") Integer vid){
		ApiResponse response = spVehicleService.deleteVehicle(vid);
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@PostMapping("/addVehicleImages/{vid}")
	public ResponseEntity<ApiResponse> addVehicleImages(@PathVariable(name="vid") Integer vid, ArrayList<MultipartFile> files) throws IOException{
		ApiResponse response = viService.uploadImages(vid,files);
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@PatchMapping("/updateCharges/{chargesId}")
	public ResponseEntity<ApiResponse> updateCharges(@PathVariable(name="chargesId") Integer chargesId, @RequestBody AddVehicleChargesModel model){
		ApiResponse response = chargesService.updateCharges(chargesId,model);	
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(200).body(response);
		}
	}
	@GetMapping("/viewCharges/{vmid}")
	public ResponseEntity<ApiResponse> viewCharges(@PathVariable(name="vmid") Integer vmId) {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApiResponse response = chargesService.viewVehicleCharges(obj,vmId);
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(200).body(response);
		}
	}
	
	@GetMapping("/viewRequest")
	public ResponseEntity<ApiResponse> viewRequest()
	{
		ApiResponse response = spService.viewRequest();
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@GetMapping("/spVehicles")
	public ResponseEntity<ApiResponse> spVehicles()
	{
		ApiResponse response = spService.requestVehicles();
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(500).body(response);
		}

	}
}
