package com.wheelyDeals.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wheelyDeals.entities.User;
import com.wheelyDeals.repositories.UserRepo;


@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
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
}