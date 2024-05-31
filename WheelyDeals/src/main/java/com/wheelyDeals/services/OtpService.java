package com.wheelyDeals.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wheelyDeals.entities.Otp;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.repositories.OtpRepo;

@Service
public class OtpService {

	@Autowired
	private OtpRepo otpRepo;

	public Optional<Otp> findByUser(User user) {
		// TODO Auto-generated method stub
		return otpRepo.findByUser(user);
	}

	public void deleteOtp(Otp otp) {
		otpRepo.delete(otp);		
	}


}
