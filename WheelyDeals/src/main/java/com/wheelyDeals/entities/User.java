package com.wheelyDeals.entities;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) 
public class User implements UserDetails
{
	public User(String email, String password, String role, Boolean activeStatus,LocalDate regDate,Boolean isblock) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
		this.activeStatus = activeStatus;
		this.regDate = regDate;
		this.isblock = isblock;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="userId")
	private Integer userId;
	
	@Column(name="email", nullable = false, unique = true)
	private String email;
	
	@Column(name="password", nullable = false)
	@JsonIgnore
	private String password;
	
	@Column(name="role", nullable = false)
	private String role;
	
	@Column(name="active_status", nullable = false)
	private Boolean activeStatus;

	@Column(name="registration_date")
	private LocalDate regDate;
	
	@Column(name="isblock")
	private Boolean isblock;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.getRole());
		return Arrays.asList(authority);
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

}
