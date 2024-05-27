package com.wheelyDeals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wheelyDeals.model.CustomerRegistrationModel;
import com.wheelyDeals.services.CustomerService;
import com.wheelyDeals.utils.ApiResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/web")
@RestController
public class WebController 
{
	@Autowired
	private CustomerService custService;
	
	@PostMapping("/saveCustomer")
	public ApiResponse saveCust(@RequestBody CustomerRegistrationModel cusmodel)
	{
		ApiResponse response= custService.saveCust(cusmodel);
		return response;
	}
	
}
