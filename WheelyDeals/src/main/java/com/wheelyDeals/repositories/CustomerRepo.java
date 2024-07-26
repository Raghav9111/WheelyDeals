package com.wheelyDeals.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wheelyDeals.entities.Customer;
import com.wheelyDeals.entities.User;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> 
{
	@Query("select c from Customer c where c.role=?1 and c.isblock=?2")
   Optional<List<Customer>> findByRoleAndIsblock(String role , Boolean status);

}
