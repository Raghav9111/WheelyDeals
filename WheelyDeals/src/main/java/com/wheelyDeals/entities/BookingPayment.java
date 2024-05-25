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
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingPayment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentId;
	
	@ManyToOne
	@JoinColumn
	private Booking booking;
	
	@Column(nullable = false)
	private Date paymentDate;
	
	@Column(nullable = false)
	private Integer amount;
	
	@Column(nullable = false)
	private String paymentType;
	
	@Column(nullable = false)
	private String mode;
	
	@Column(nullable = false, unique = true)
	private String referenceNumber;
	
	
}
