package com.wheelyDeals.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class VehicleMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer vehicleMasterId;
	
	@Column(unique = true, nullable = false)
	private String vehicleModel;
	
	@Column(nullable = false)
	private String vehicleType;
	
	@Column()
	private Integer seats;
	
	@Column()
	private String vehicleCapacity;
	
	@Column(nullable = false)
	private String vehicleImage;
	
}
