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

public class Otp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer otpId;
	
	@ManyToOne
	@JoinColumn(unique = true, nullable = false)
	private User user;
	
	@Column(nullable = false)
	private String otpNumber;

	public Otp(User user, String otpNumber) {
		super();
		this.user = user;
		this.otpNumber = otpNumber;
	}
	
}


