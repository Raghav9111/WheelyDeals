package com.wheelyDeals.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wheelyDeals.entities.Customer;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.model.RegistrationDateModel;
import com.wheelyDeals.services.CustomerService;
import com.wheelyDeals.services.UserService;
import com.wheelyDeals.utils.ApiResponse;



@RequestMapping("/admin")
@RestController
public class AdminController extends BaseController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserService userService;
	
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
	
}
