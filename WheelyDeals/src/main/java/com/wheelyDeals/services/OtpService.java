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

	public Otp findByUser(User user) {
		Optional<Otp> obj = otpRepo.findByUser(user);
		if(obj.isPresent())
			return obj.get();
		else
			return null;
	}

	public void deleteOtp(Otp otp) {
		otpRepo.delete(otp);		
	}


}
