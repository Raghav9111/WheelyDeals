package com.wheelyDeals.entities;

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
	@JoinColumn
	private ServiceProvider serviceProvider;
	
	@ManyToOne
	@JoinColumn
	private Customer customer;
	
	@ManyToOne
	@JoinColumn
	private VehicleRequest vehicleRequest;
	
	@ManyToOne
	@JoinColumn
	private VehicleRequestResponse vehicleRequestResponse;
	
	@Column
	private Date bookingDate;
	
	@Column
	private String driverName;
	
	@Column
	private String driverPhone;
	
	@Column(nullable = false)
	private Date tripStartDate;
	

	@Column(nullable = false)
	private Date tripEndDate;
	
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
