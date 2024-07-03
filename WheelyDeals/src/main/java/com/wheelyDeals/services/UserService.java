package com.wheelyDeals.services;

import java.time.LocalDate;
import java.util.List;
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
import com.wheelyDeals.entities.ServiceProvider;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.model.CustomerRegistrationModel;
import com.wheelyDeals.model.OtpVerifyModel;
import com.wheelyDeals.model.RegistrationDateModel;
import com.wheelyDeals.model.UpdatePasswordModel;
import com.wheelyDeals.repositories.CustomerRepo;
import com.wheelyDeals.repositories.OtpRepo;
import com.wheelyDeals.repositories.ServiceProviderRepo;
import com.wheelyDeals.repositories.UserRepo;
import com.wheelyDeals.utils.ApiResponse;


@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ServiceProviderRepo spRepo;
	
	@Autowired
	private CustomerRepo crepo;
	
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
	
	public User findByEmail(String email) 
	{
		Optional<User> obj = userRepo.findByEmail(email);
		if(obj.isPresent()) {
			return obj.get();
		}
		else {
			return null;
		}
	}

	public void update(User user) 
	{
		userRepo.save(user);
	}

	public ApiResponse updatePass(Object obj,int check) {
		try {
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
				User user = findByEmail(model.getEmail());
				if(user != null)
				{
					if(model.newPass.matches(model.confirmPass))
					{
						user.setPassword(encoder.encode(model.confirmPass));
						userRepo.save(user);
						Otp otp = otpService.findByUser(user);
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
		catch (Exception e) {
			return new ApiResponse(false, "Password change Exception", e.getMessage());
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
			Otp otp = otpService.findByUser(user);
			
			if(otp!=null)
			{
				otp.setOtpNumber(ot);
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
	
		public User blockUser(Integer uId) {
			User user = userRepo.getById(uId);

			if (user.getIsblock() == true) {
				user.setIsblock(false);
				userRepo.save(user);
				return user;

			} else {
				user.setIsblock(true);
				userRepo.save(user);
				return user;
			}

		}

		public ApiResponse getByDates(RegistrationDateModel model)
		{
			try {
				List<User> user = userRepo.findUsersByRegDateBetween(model.getFromDate(), model.getToDate(),"ROLE_ADMIN");
				if(user != null) {
					return new ApiResponse(true,"Users List" , user);
				}
				else {
					return new ApiResponse(false, "Can't find users");
				}
			}
			catch (Exception e) {
				return new ApiResponse(false, "Can't find users", e.getMessage());
			}
		}

		public ApiResponse verifyEmail(User user) {
			if(user != null) {
				user.setActiveStatus(true);
				update(user);
				return new ApiResponse(true, "Account Activated !");
			}else {
				return new ApiResponse(false, "Account not active");
			}
		}

		public ApiResponse getByStatus(Boolean status, String role) 
		{
				if(role.equals("customer")) 
				{
					Optional<List<Customer>> obj = crepo.findByRoleAndIsblock("ROLE_CUSTOMER", status);
					if(obj.isPresent())
					{
						List<Customer> clist = obj.get();
						return new ApiResponse(true, "Active customer list", clist);
					}
					else
					{
						return new ApiResponse(false, "Active customer list failed");
					}
				}
				else
				{
					Optional<List<ServiceProvider>> obj = spRepo.findByRoleAndIsblock("ROLE_SERVICEPROVIDER", status);
					if(obj.isPresent())
					{
						List<ServiceProvider> splist = obj.get();
						return new ApiResponse(true, "Active service provider list", splist);
					}
					else
					{
						return new ApiResponse(false, "Active service provider list failed");
					}
				}
		}
}