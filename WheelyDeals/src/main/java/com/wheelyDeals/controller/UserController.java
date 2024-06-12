
package com.wheelyDeals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.wheelyDeals.model.UpdatePasswordModel;
import com.wheelyDeals.services.UserService;
import com.wheelyDeals.utils.ApiResponse;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController
{
	@Autowired
	private UserService userService;
	
	@PatchMapping("/updatePassword")
	public ResponseEntity<ApiResponse> updatePassword(@RequestBody UpdatePasswordModel model )
	{
		ApiResponse response = userService.updatePass(model,1);
		if(response.getStatus())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(500).body(response);
	}
}
