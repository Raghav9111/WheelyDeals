package com.wheelyDeals.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddVehicleMasterModel 
{
	private String vehicleModel;
	
	private String vehicleType;
	
	private Integer seats;
	
	private String vehicleCapacity;
	
	private String vehicleImage;

}
