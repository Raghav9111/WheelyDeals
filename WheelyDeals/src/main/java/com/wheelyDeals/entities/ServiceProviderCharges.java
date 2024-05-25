package com.wheelyDeals.entities;

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
public class ServiceProviderCharges {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer chargesId;
	
	@ManyToOne
	@JoinColumn(name="service_provider", nullable = false)
	private ServiceProvider serviceProvider;
	
	@ManyToOne
	@JoinColumn(name="vehicle_master", nullable = false)
	private ServiceProvider vehicleMaster;
	
	@Column(nullable = false)
	private Integer minPerDayKm;
	
	@Column
	private Float petrolRatePerKm;
	
	@Column
	private Float diselRatePerKm;
	
	@Column
	private Float cngRatePerKm;
	
	@Column
	private Float evRatePerKm;
	
	@Column
	private Float acRatePerKm;
	
}
