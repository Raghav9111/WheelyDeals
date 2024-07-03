package com.wheelyDeals.entities;

import java.util.Date;
import java.util.List;

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
public class VehicleImages {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer vehicleImageId;
	
	@Column
	private String image1;
	@Column
	private String image2;
	@Column
	private String image3;
	@Column
	private String image4;
	
	@ManyToOne
	@JoinColumn(name="service_provider_vehicle")
	private ServiceProviderVehicle serviceProviderVehicle;

	public VehicleImages(String image1, String image2, String image3, String image4,
			ServiceProviderVehicle serviceProviderVehicle) {
		super();
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;
		this.image4 = image4;
		this.serviceProviderVehicle = serviceProviderVehicle;
	}

	
	
	
}
