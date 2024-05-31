package com.wheelyDeals.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wheelyDeals.entities.Customer;
import com.wheelyDeals.entities.Otp;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.model.CustomerRegistrationModel;
import com.wheelyDeals.model.OtpVerifyModel;
import com.wheelyDeals.model.UpdatePasswordModel;
import com.wheelyDeals.repositories.OtpRepo;
import com.wheelyDeals.repositories.UserRepo;
import com.wheelyDeals.utils.ApiResponse;


@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private OtpRepo otprepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByEmail(username).get();
	}

	public User getById(Integer int1) 
	{
		User user = userRepo.findById(int1).get();
		return user;
	}
	
	public Optional<User> findByEmail(String email) 
	{
		return userRepo.findByEmail(email);
	}

	public void update(User user) 
	{
		userRepo.save(user);
	}

	public ApiResponse updatePass(Object obj,int check) {
		if(check ==1)
		{
			UpdatePasswordModel model = (UpdatePasswordModel)obj;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = (User)principal;			
			if(!encoder.matches(model.getOldPass(),user.getPassword() )){
				return new ApiResponse(false, "old password is not correct");
			}
			else{
				if(!model.getNewPass().equals(model.getConfirmPass())){
					return new ApiResponse(false, "new password and confirm password does not match") ;
				}
				else{
					user.setPassword(encoder.encode(model.getNewPass()));
					userRepo.save(user);
					return new ApiResponse(true, "Password Changed");
				}
			}	
		}
		else
		{
			OtpVerifyModel model = (OtpVerifyModel)obj;
			Optional<User> op = findByEmail(model.getEmail());
			if(op.isPresent())
			{
				User user = op.get();
				if(model.newPass.matches(model.confirmPass))
				{
					user.setPassword(encoder.encode(model.confirmPass));
					userRepo.save(user);
					Otp otp = otpService.findByUser(user).get();
					otpService.deleteOtp(otp);
					return new ApiResponse(true, "password changed");
				}
				else
					return new ApiResponse(false, "Password not Matched");
			}
			else {
				return new ApiResponse(false, "Can't get User");
			}
		}
	}
	
	public ApiResponse sendOtp(String email, User user) {
		ApiResponse response = null;
		Otp ob = null;
		boolean status;
		try
		{
			Random random = new Random();
			
			int number = 100000 + random.nextInt(900000);
			String ot=String.valueOf(number);
			System.out.println(ot);
			
			Optional<Otp> op = otpService.findByUser(user);
			
			if(op.isPresent())
			{
				 ob =  op.get();
				System.out.println(ob);
				ob.setOtpNumber(ot);
				
				otprepo.save(ob);
				status = true;
			
				response = new ApiResponse(true, "otp set !");
			}
			else
			{
				ob = new Otp(user, ot);
				otprepo.save(ob);
				status = true;
				response= new ApiResponse(true,"otp saved") ;
			}
			if(status){
				mailService.otpVerificationMail(email, ot);
			}
		}
		catch(Exception ex){	
			response = new ApiResponse(false, "otp was not sent !", ex.getMessage());
		}
		return response;	
		}
}