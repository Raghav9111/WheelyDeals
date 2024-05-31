
package com.wheelyDeals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wheelyDeals.model.CustomerRegistrationModel;
import com.wheelyDeals.model.UpdatePasswordModel;
import com.wheelyDeals.services.UserService;
import com.wheelyDeals.utils.ApiResponse;

@RestController
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@PatchMapping("/updatePassword")
	public ApiResponse updatePassword(@RequestBody UpdatePasswordModel model )
	{
		ApiResponse res = userService.updatePass(model,1);
		return res;
	}
}
