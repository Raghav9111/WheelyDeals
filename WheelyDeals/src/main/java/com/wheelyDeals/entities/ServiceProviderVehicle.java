package com.wheelyDeals.entities;

import java.util.Date;

import com.wheelyDeals.model.AddSPVehicleModel;

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
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer spVehicleId;
	
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

	public ServiceProviderVehicle(ServiceProvider serviceProvider, VehicleMaster vehicleMaster,
			String registrationNumber, String rcCard, String fuelType, String status) {
		super();
		this.serviceProvider = serviceProvider;
		this.vehicleMaster = vehicleMaster;
		this.registrationNumber = registrationNumber;
		this.rcCard = rcCard;
		this.fuelType = fuelType;
		this.status = status;
	}

	public Boolean checkFuelStatus(ServiceProviderCharges spc, AddSPVehicleModel model) {
		switch(model.getFuelType()) {
			case "Petrol":
				if(spc.getPetrolRatePerKm() == null || spc.getPetrolRatePerKm() == 0) {
					System.err.println("nhi aaya");
					return false;
				}
				else {
					System.out.println("aaya");
				    return true;
				}
			case "Diesel":
				if(spc.getDieselRatePerKm() == null || spc.getDieselRatePerKm() == 0) {
					return false;
				}
				else {
					return true;
				}
			case "CNG":
				if(spc.getCngRatePerKm() == null || spc.getCngRatePerKm() == 0) {
					return false;
				}
				else {
				    return true;
				}
			case "EV":
				if(spc.getEvRatePerKm() == null || spc.getEvRatePerKm() == 0) {
					return false;
				}
				else {
					return true;
				}
			default:
				System.err.println("yha aaya");
				return false;
		}
	}
	
	
}
