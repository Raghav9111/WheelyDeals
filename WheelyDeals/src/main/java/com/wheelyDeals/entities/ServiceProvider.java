package com.wheelyDeals.entities;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="service_provider")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProvider extends User{
	
	
	

	public ServiceProvider(String email, String password, String role, Boolean activeStatus, LocalDate regDate,
			Boolean isblock, String serviceProviderName, String serviceProviderAddress, String serviceProviderMobile,
			String serviceProviderType, Float rating) {
		super(email, password, role, activeStatus, regDate, isblock);
		this.serviceProviderName = serviceProviderName;
		this.serviceProviderAddress = serviceProviderAddress;
		this.serviceProviderMobile = serviceProviderMobile;
		this.serviceProviderType = serviceProviderType;
		this.rating = rating;
	}

	@Column(name="service_provider_name",nullable = false)
	private String serviceProviderName;
	
	@Column(name="service_provider_address",nullable = false)
	private String serviceProviderAddress;

	@Column(name="service_provider_mobile",nullable = false, unique = true)
	private String serviceProviderMobile;
	
	@Column(name="service_provider_type", nullable = false)
	private String serviceProviderType;
	
	@Column(name="rating")
	private Float rating;
	
	
}
