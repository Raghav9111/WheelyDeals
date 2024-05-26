package com.wheelyDeals.entities;

import java.time.LocalDate;
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
@AllArgsConstructor
@NoArgsConstructor
@Data

public class VehicleRequestResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer responseId;
	
	@ManyToOne
	@JoinColumn(name="service_provider")
	private ServiceProvider serviceProvider;
	
	@ManyToOne
	@JoinColumn(name="vehicle_request")
	private VehicleRequest vehicleRequest;
	
	@ManyToOne
	@JoinColumn(name="service_provider_vehicle")
	private ServiceProviderVehicle serviceProviderVehicle;
	
	@Column(nullable=false)
	private LocalDate responseDate;
	
	@Column(nullable = false)
	private String responseStatus;
	
}

