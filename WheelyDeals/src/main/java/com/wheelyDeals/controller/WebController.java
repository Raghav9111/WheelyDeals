package com.wheelyDeals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/web")
@RestController
public class WebController extends BaseController
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
	public ResponseEntity<ApiResponse> saveCust(@Valid @RequestBody CustomerRegistrationModel cusmodel)
	{
		ApiResponse response= custService.saveCust(cusmodel);
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);
		
	}
	
	@PostMapping("/saveServiceProvider")
	public ResponseEntity<ApiResponse> saveServProvider(@RequestBody ServiceProviderRegistrationModel model)
	{	
		ApiResponse response= providerService.saveProvider(model);
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody LoginModel model)
	{	try {
			authmanager.authenticate(new UsernamePasswordAuthenticationToken(model.getEmail(),model.getPassword()));

		User user =  (User) userService.loadUserByUsername(model.getEmail());
			
			if(user.getActiveStatus())
			{
				final String token = jwtToken.generateToken(user);
				
				LoginResponseModel lres = new LoginResponseModel(user.getEmail(), token, user.getRole());

				ApiResponse response = new ApiResponse(true, "Login Success !", lres);

				return ResponseEntity.status(200).body(response);
			}else 
			{
				ApiResponse response = new ApiResponse(false , "Login failed");

				return ResponseEntity.status(200).body(response);
			}
		}catch(Exception ex) {
			ApiResponse response = new ApiResponse(false , "Login failed");

			return ResponseEntity.status(400).body(response);
		}
	}

	@GetMapping("/verify/{mail}")
	public ResponseEntity<ApiResponse> verifyMail(@PathVariable(name = "mail") String email) 
	{
		User user = userService.findByEmail(email);
		ApiResponse response = userService.verifyEmail(user);
		if(response.getStatus()) {
			return ResponseEntity.status(200).body(response);
		}
		else {
			return ResponseEntity.status(400).body(response);	
		}
	}
	
	@PatchMapping("/forget_pass")
	public ResponseEntity<ApiResponse> updatePassword(@RequestBody UpdatePasswordModel model )
	{
		User user = userService.findByEmail(model.getEmail());
		if(user != null) {
			ApiResponse response= userService.sendOtp(model.getEmail(),user);
			if (response.getStatus())
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
			else
				return ResponseEntity.status(500).body(response);
		}
		else{
			ApiResponse response=  new ApiResponse(false, "email not found !");
			return ResponseEntity.status(500).body(response);

		}
	}
	
	@PostMapping("/verifyOtp")
	public ResponseEntity<ApiResponse> verifyOtp(@RequestBody OtpVerifyModel model)
	{
		
		User user = userService.findByEmail(model.getEmail());
		if(user!=null){
			Otp otp = otpService.findByUser(user);	
			if(otp!=null){
				Boolean b = otp.getOtpNumber().matches(model.otp);
				if(b){
					ApiResponse response  = new ApiResponse(true, "OTP is Correct !");
					return ResponseEntity.status(200).body(response);
				}
				else{
					ApiResponse response  = new ApiResponse(true, "OTP is Not Correct !");
					return ResponseEntity.status(500).body(response);
				}
			}
			else{
				ApiResponse response  = new ApiResponse(true, "User not Present !");
				return ResponseEntity.status(500).body(response);
				}
			}
		else{
			ApiResponse response  = new ApiResponse(true, "User not found !");
			return ResponseEntity.status(500).body(response);	
		}
	}
	
	@PatchMapping("/changePass")
	public ResponseEntity<ApiResponse> changePass(@RequestBody OtpVerifyModel model)
	{
		ApiResponse res = null;
		try{
			 res = userService.updatePass(model,2);
			 return ResponseEntity.status(200).body(res);
		}
		catch (Exception ex){
			return ResponseEntity.status(500).body(res);
		}
	}
}
