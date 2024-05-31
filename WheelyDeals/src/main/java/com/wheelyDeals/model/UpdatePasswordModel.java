package com.wheelyDeals.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordModel {
public String oldPass;
public String newPass;
public String confirmPass;
public String email;

public UpdatePasswordModel(String oldPass, String newPass, String confirmPass) {
	super();
	this.oldPass = oldPass;
	this.newPass = newPass;
	this.confirmPass = confirmPass;
}
public UpdatePasswordModel(String email) {
	super();
	this.email = email;
}
public UpdatePasswordModel(String newPass, String confirmPass) {
	super();
	this.newPass = newPass;
	this.confirmPass = confirmPass;
}









}
