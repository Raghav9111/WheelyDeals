package com.wheelyDeals.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistrationModel 
{
	private String email;
	
	private String password;
	
	private String customerName;
	
	private String mobile;
	
}
