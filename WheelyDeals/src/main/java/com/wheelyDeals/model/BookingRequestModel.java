package com.wheelyDeals.model;

import java.time.LocalDate;

import com.wheelyDeals.entities.VehicleMaster;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingRequestModel 
{	
	private Integer vehicleMaster;
	
	private String source;
	
	private String destination;
	
	private String description;
	
	private LocalDate tripStartDate;
	
	private LocalDate tripEndDate;
	
	private Boolean isAc;
	
	private String fuelType;	
	
	private Float distance;
}

