package com.wheelyDeals.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wheelyDeals.entities.Customer;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.services.CustomerService;
import com.wheelyDeals.services.UserService;
import com.wheelyDeals.utils.ApiResponse;

@RequestMapping("/admin")
@RestController
public class AdminController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/viewAllCustomer")
	public ApiResponse viewAllCustomer() {
		try {
			List<Customer> customerList = customerService.viewAll();
			return new ApiResponse(true, "Customer List", customerList);
		}catch (Exception e) {
			return new ApiResponse(false, "Customer List Failed");
		}
	}
	@GetMapping("/blockUser/{uId}")
	public ApiResponse blockUser(@PathVariable("uId") Integer uId) {
		try {
			User user = userService.blockUser(uId);
			if(user.getIsblock()==true) {
				return new ApiResponse(true, "User Blocked");
			}
			else {
				return new ApiResponse(true, "User Unblocked");
			}
		}catch (Exception e) {
			return new ApiResponse(false, "User Block Failed", e.getMessage());
		}
		
	}
}
