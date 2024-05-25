package com.wheelyDeals.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderVehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer serivceProviderVehicle;
	
	@ManyToOne
	@JoinColumn(name="service_provider")
	private ServiceProvider serviceProvider;
	
	@ManyToOne
	@JoinColumn(name="vehicle_master")
	private VehicleMaster vehicleMaster;
	
	@Column(unique = true, nullable = false)
	private String registrationNumber;
	
	@Column(nullable = false)
	private String rcCard;
	
	@Column(nullable = false)
	private String fuelType;
	
	@Column(nullable = false)
	private String status;
	
	
}
