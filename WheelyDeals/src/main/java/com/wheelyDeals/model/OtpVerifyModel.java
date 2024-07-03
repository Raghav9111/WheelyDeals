package com.wheelyDeals.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerifyModel {

	public String email;
	public String otp;
	public String newPass;
	public String confirmPass;
	public OtpVerifyModel(String email, String newPass, String confirmPass) {
		super();
		this.email = email;
		this.newPass = newPass;
		this.confirmPass = confirmPass;
	}
	public OtpVerifyModel(String email, String otp) {
		super();
		this.email = email;
		this.otp = otp;
	}
	

}
