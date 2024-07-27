package com.wheelyDeals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wheelyDeals.model.BookingRequestModel;
import com.wheelyDeals.services.CustomerService;
import com.wheelyDeals.utils.ApiResponse;

@RestController
@RequestMapping("/customer")
public class CustomerController 
{
	@Autowired
	private CustomerService cusService;

	
	@PostMapping("/requestBooking")
	public ResponseEntity<ApiResponse> requestBooking(@RequestBody BookingRequestModel model)
	{
		
		ApiResponse response = cusService.requestBooking(model);;
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(500).body(response);
		}
	}
}
