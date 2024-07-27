package com.wheelyDeals.entities;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User{
	
	
	@Column(name="customer_name", nullable = false)
	private String customerName;
	
	@Column(name="mobile", nullable = false, unique = true)
	private String mobile;

	public Customer(String email, String password, String role, Boolean activeStatus, LocalDate regDate,
			Boolean isblock, String customerName, String mobile) {
		super(email, password, role, activeStatus, regDate, isblock);
		this.customerName = customerName;
		this.mobile = mobile;
	}
}
