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
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookingId;
	
	@ManyToOne
	@JoinColumn(name="service_provider")
	private ServiceProvider serviceProvider;
	
	@ManyToOne
	@JoinColumn(name="customer")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="vehicle_request")
	private VehicleRequest vehicleRequest;
	
	@ManyToOne
	@JoinColumn(name="vehicle_request_response")
	private VehicleRequestResponse vehicleRequestResponse;
	
	@Column
	private LocalDate bookingDate;
	
	@Column
	private String driverName;
	
	@Column
	private String driverPhone;
	
	@Column(nullable = false)
	private LocalDate tripStartDate;
	

	@Column(nullable = false)
	private LocalDate tripEndDate;
	
	@Column
	private Integer startMeter;
	
	@Column
	private Integer endMeter;
	
	@Column
	private Float totalAmount;
	
	@Column
	private String pickUpPoint;
	
	@Column
	private String dropPoint;
	
	@Column(nullable = false)
	private String bookingStatus;
	
}
