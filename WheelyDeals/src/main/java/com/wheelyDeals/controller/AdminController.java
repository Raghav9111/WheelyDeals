package com.wheelyDeals.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wheelyDeals.entities.Customer;
import com.wheelyDeals.entities.ServiceProvider;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.entities.VehicleMaster;
import com.wheelyDeals.model.AddVehicleMasterModel;
import com.wheelyDeals.model.RegistrationDateModel;
import com.wheelyDeals.services.CustomerService;
import com.wheelyDeals.services.ServiceProviderService;
import com.wheelyDeals.services.UserService;
import com.wheelyDeals.services.VehicleMasterService;
import com.wheelyDeals.utils.ApiResponse;

import jakarta.mail.Multipart;

import org.springframework.web.bind.annotation.RequestParam;




@RequestMapping("/admin")
@RestController
public class AdminController extends BaseController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VehicleMasterService vmService;
	
	@Autowired
	private ServiceProviderService spService;
	
	@GetMapping("/viewAllCustomer")
	public ResponseEntity<ApiResponse> viewAllCustomer() {
		ApiResponse response;
		try {
			List<Customer> customerList = customerService.viewAll();
			response = new ApiResponse(true, "Customer List", customerList);
			return ResponseEntity.status(200).body(response);
		}catch (Exception e) {
			response = new ApiResponse(false, "Customer List Failed");
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@GetMapping("/viewAllServiceProvider")
	public ResponseEntity<ApiResponse> viewAllServiceProvider() {
		ApiResponse response;
		try {
			List<ServiceProvider> spList = spService.viewAll();
			response = new ApiResponse(true, "Service Provider List", spList);
			return ResponseEntity.status(200).body(response);
		}catch (Exception e) {
			response = new ApiResponse(false, "Service Provider List Failed");
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@GetMapping("/statusFilter/{status}/{role}")
	public ResponseEntity<ApiResponse> statusFilter(@PathVariable(name="status") Boolean status, @PathVariable(name="role") String role)
	{
		ApiResponse response = userService.getByStatus(status,role);
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@GetMapping("/blockUser/{uId}")
	public ResponseEntity<ApiResponse> blockUser(@PathVariable("uId") Integer uId) {
		ApiResponse response;
		try {
			User user = userService.blockUser(uId);
			if(user.getIsblock()==true) {
				response = new ApiResponse(true, "User Blocked");
				return ResponseEntity.status(200).body(response);
			}
			else {
				response = new ApiResponse(true, "User Unblocked");
				return ResponseEntity.status(200).body(response);
			}
		}catch (Exception e) {
			response = new ApiResponse(false, "User Block Failed", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@PostMapping("/userByDate")
	public ResponseEntity<ApiResponse>  userByDate(@RequestBody RegistrationDateModel model) {
		ApiResponse response = userService.getByDates(model);
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@PostMapping("/addMasterVehicle")
	public ResponseEntity<ApiResponse> addMasterVehicle(@RequestBody AddVehicleMasterModel model,@RequestParam("image") MultipartFile file) throws IOException 
	{	
		byte arr[] = file.getBytes();
		
		String fileName = file.getOriginalFilename();		
		String extension = fileName.substring(fileName.lastIndexOf("."));
		String uploadFile =  UUID.randomUUID().toString()+extension;	
		File fileObj = new File("C:\\Users\\Hp\\git\\WheelyDeals\\WheelyDeals\\src\\main\\resources\\static\\images", uploadFile);
		FileOutputStream fos = new FileOutputStream(fileObj);
		fos.write(arr);
		fos.flush();
		fos.close();		
		String filePath =  fileObj.getAbsolutePath();
		model.setVehicleImage(filePath);
		ApiResponse response= vmService.addVehicle(model);
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);

	}
	
	@GetMapping("/allVehicles")
	public ResponseEntity<ApiResponse> viewAllVehicles() 
	{
		ApiResponse response;
		try {
			List<VehicleMaster> vehicleList = vmService.viewAll();
			response = new ApiResponse(true, "Vehicle List", vehicleList);
			return ResponseEntity.status(200).body(response);
		}catch (Exception e) {
			response = new ApiResponse(false, "Customer List Failed");
			return ResponseEntity.status(500).body(response);
		}
	}
	
}
