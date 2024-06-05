package com.wheelyDeals.model;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistrationModel 
{
	@NotNull
	@UniqueElements
	private String email;
	
	
	private String password;
	
	private String customerName;
	
	private String mobile;
	
}
