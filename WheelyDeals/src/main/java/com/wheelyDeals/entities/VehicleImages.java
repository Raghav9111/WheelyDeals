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
	private List<String> images;
	
	@ManyToOne
	@JoinColumn
	private ServiceProviderVehicle serviceProviderVehicle;
}
