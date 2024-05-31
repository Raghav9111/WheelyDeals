package com.wheelyDeals.controller;

import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wheelyDeals.config.JwtTokenUtil;
import com.wheelyDeals.entities.Otp;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.model.CustomerRegistrationModel;
import com.wheelyDeals.model.OtpVerifyModel;
import com.wheelyDeals.model.LoginModel;
import com.wheelyDeals.model.LoginResponseModel;
import com.wheelyDeals.model.ServiceProviderRegistrationModel;
import com.wheelyDeals.model.UpdatePasswordModel;
import com.wheelyDeals.services.CustomerService;
import com.wheelyDeals.services.OtpService;
import com.wheelyDeals.services.ServiceProviderService;
import com.wheelyDeals.services.UserService;
import com.wheelyDeals.utils.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/web")
@RestController
public class WebController 
{
	@Autowired
	private AuthenticationManager authmanager;

	@Autowired
	private ServiceProviderService providerService;
	
	@Autowired
	private CustomerService custService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private JwtTokenUtil jwtToken;
	
	
	@PostMapping("/saveCustomer")
	public ApiResponse saveCust(@RequestBody CustomerRegistrationModel cusmodel)
	{
		ApiResponse response= custService.saveCust(cusmodel);
		return response;
	}
	
	@PostMapping("/saveServiceProvider")
	public ApiResponse saveServProvider(@RequestBody ServiceProviderRegistrationModel model)
	{
		ApiResponse response= providerService.saveProvider(model);
		return response;
	}
	
	@PostMapping("/login")
	public ApiResponse login(@RequestBody LoginModel model)
	{

		try {
			authmanager.authenticate(new UsernamePasswordAuthenticationToken(model.getEmail(),model.getPassword()));

		User user =  (User) userService.loadUserByUsername(model.getEmail());
			
			if(user.getActiveStatus())
			{
				final String token = jwtToken.generateToken(user);
				
				LoginResponseModel lres = new LoginResponseModel(user.getEmail(), token, user.getRole());
				
				return new ApiResponse(true, "Login Success !", lres);
			}else 
			{
				return new ApiResponse(false, "Inactive Account , Please Activate it through Mail !");
			}
		}catch(Exception ex) {
			return new ApiResponse(false, "Login Failed !", null,ex.getMessage());
		}
	}

	@GetMapping("/verify/{mail}")
	public ApiResponse verifyMail(@PathVariable(name = "mail") String email) 
	{
		Optional<User> op = userService.findByEmail(email);
		if(op.isPresent()) 
		{
			User user = op.get();
			user.setActiveStatus(true);
			userService.update(user);
			
			return new ApiResponse(true, "Account Activated !");
		}else {
			return new ApiResponse(false, "Wrong Email !");
		}
	}
	
	@PatchMapping("/forget_pass")
	public ApiResponse updatePassword(@RequestBody UpdatePasswordModel model )
	{
		Optional<User> op = userService.findByEmail(model.getEmail());
		if(op.isPresent())
		{
			User user = op.get();
			ApiResponse res= userService.sendOtp(model.getEmail(),user);
			 return new ApiResponse(true, "otp sent");
		}
		else
			return new ApiResponse(false, "email not found !");
	
		
	}
	
	@PostMapping("/verifyOtp")
	public ApiResponse verifyOtp(@RequestBody OtpVerifyModel model)
	{
		ApiResponse res = null;
		
		Optional<User> op = userService.findByEmail(model.getEmail());
		if(op.isPresent())
		{
			User user = op.get();
Optional<Otp> otp = otpService.findByUser(user);
			
			if(otp.isPresent())
			{
				Otp ob =  otp.get();
				
				Boolean b = ob.getOtpNumber().matches(model.otp);
				if(b)
				{

					res = new ApiResponse(true, "OTP is Correct !");
				}
				else
				{
					res= new ApiResponse(false, "OTP is incorrect !");
				}
			}
		}
		else
			return new ApiResponse(false, "email not found !");
		return res;
		
		
	}
	
	@PostMapping("/changePass")
	public ApiResponse changePass(@RequestBody OtpVerifyModel model)
	{
		ApiResponse res = null;
		
		try
		{
			 res = userService.updatePass(model,2);
			 return new ApiResponse(true, "password changed");
		}
		catch (Exception ex)
		{
			return new ApiResponse(false, "error", ex.getMessage());
		}
		
	}
	
}
