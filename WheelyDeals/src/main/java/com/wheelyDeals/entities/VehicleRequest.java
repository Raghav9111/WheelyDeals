package com.wheelyDeals.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer vehicleRequestId;
	
	@ManyToOne
	@JoinColumn(name="customer",nullable = false)
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="vehicle_master",nullable = false)
	private VehicleMaster vehicleMaster;
	
	@Column(nullable = false)
	private String source;
	
	@Column(nullable = false)
	private String destination;
	
	@Column(nullable = false)
	private LocalDate requestDate;
	
	@Column
	private String description;
	
	@Column(nullable = false)
	private LocalDate tripStartDate;
	
	@Column(nullable = false)
	private LocalDate tripEndDate;
	
	public VehicleRequest(Customer customer, VehicleMaster vehicleMaster, String source, String destination,
			LocalDate requestDate, String description, LocalDate tripStartDate, LocalDate tripEndDate, Boolean isAc,
			String fuelType, String requestStatus, Float distance) {
		super();
		this.customer = customer;
		this.vehicleMaster = vehicleMaster;
		this.source = source;
		this.destination = destination;
		this.requestDate = requestDate;
		this.description = description;
		this.tripStartDate = tripStartDate;
		this.tripEndDate = tripEndDate;
		this.isAc = isAc;
		this.fuelType = fuelType;
		this.requestStatus = requestStatus;
		this.distance = distance;
	}

	@Column(nullable = false)
	private Boolean isAc;
	
	@Column(nullable = false)
	private String fuelType;
	
	@Column(nullable = false)
	private String requestStatus;
	
	@Column(nullable = false)
	private Float distance;

	
}
