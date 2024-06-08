package com.wheelyDeals.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddVehicleChargesModel {
	private Integer vehicleMasterId;
	private Integer minPerDayKm;
	private Float petrolRatePerKm;
	private Float dieselRatePerKm;
	private Float cngRatePerKm;
	private Float evRatePerKm;
	private Float acRatePerKm;
	
}
