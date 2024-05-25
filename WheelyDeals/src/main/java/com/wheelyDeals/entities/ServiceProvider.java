package com.wheelyDeals.entities;

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
	@Column(name="service_provider_name",nullable = false)
	private String serviceProviderName;
	
	@Column(name="service_provider_address",nullable = false)
	private String serviceProviderAddress;

	@Column(name="service_provider_mobile",nullable = false, unique = true)
	private String serviceProviderMobile;
	
	@Column(name="service_provider_type", nullable = false)
	private Boolean serviceProviderType;
	
	@Column(name="rating")
	private Float rating;
	
	
}
