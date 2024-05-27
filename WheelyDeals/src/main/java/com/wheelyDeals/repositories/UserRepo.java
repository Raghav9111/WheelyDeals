package com.wheelyDeals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wheelyDeals.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>
{

}
