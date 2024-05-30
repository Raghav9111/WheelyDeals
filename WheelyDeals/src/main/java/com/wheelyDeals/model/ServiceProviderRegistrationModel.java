package com.wheelyDeals.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderRegistrationModel 
{
	private String email;
	private String password;
	private String serviceProviderName;
	private String serviceProviderAddress;
	private String serviceProviderMobile;
	private String serviceProviderType;
	private Float rating;
}
