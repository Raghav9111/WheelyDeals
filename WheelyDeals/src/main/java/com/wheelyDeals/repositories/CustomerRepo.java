package com.wheelyDeals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wheelyDeals.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer,Integer> 
{

}
