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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceFeedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer feedbackId;
	
	@Column
	private String feedback;
	
	@Column
	private Integer rating;
	
	@ManyToOne
	@JoinColumn
	private ServiceProvider serviceProvider;
	
	@ManyToOne
	@JoinColumn
	private Customer customer;
	
	@Column
	private Date feedbackDate;
}
