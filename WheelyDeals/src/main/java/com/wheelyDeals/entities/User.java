package com.wheelyDeals.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) 
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="userId")
	private Integer userId;
	
	@Column(name="email", nullable = false, unique = true)
	private String email;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="role", nullable = false)
	private String role;
	
	@Column(name="active_status", nullable = false)
	private Boolean activeStatus;

}
