package com.wheelyDeals.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class AddSPVehicleModel {
	private Integer vehicleMasterId;
	private String registrationNumber;
	private String rcCard;
	private String fuelType;

}
