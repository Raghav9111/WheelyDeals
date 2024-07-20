package com.wheelyDeals.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class VehicleMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer vehicleMasterId;
	
	@Column(unique = true, nullable = false)
	private String vehicleModel;
	
	@Column(nullable = false)
	private String vehicleType;
	
	@Column(nullable = true)
	private Integer seats;
	
	@Column()
	private Integer vehicleCapacity;
	
	@Column()
	private String vehicleImage;

	public VehicleMaster(String vehicleModel, String vehicleType, Integer seats, Integer vehicleCapacity,
			String vehicleImage) {
		super();
		this.vehicleModel = vehicleModel;
		this.vehicleType = vehicleType;
		this.seats = seats;
		this.vehicleCapacity = vehicleCapacity;
		this.vehicleImage = vehicleImage;
	}
	
	
	
}
