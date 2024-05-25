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
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer vehicleRequestId;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private VehicleMaster vehicleMaster;
	
	@Column(nullable = false)
	private String source;
	
	@Column(nullable = false)
	private String destination;
	
	@Column(nullable = false)
	private Date requestDate;
	
	@Column
	private String description;
	
	@Column(nullable = false)
	private Date tripStartDate;
	
	@Column(nullable = false)
	private Date tripEndDate;
	
	@Column(nullable = false)
	private Boolean isAc;
	
	@Column(nullable = false)
	private String fuelType;
	
	@Column(nullable = false)
	private String requestStatus;
	
}
