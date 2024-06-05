package com.wheelyDeals.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wheelyDeals.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>
{
	Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.regDate BETWEEN ?1 AND ?2 AND u.role != ?3")
	 List<User> findUsersByRegDateBetween(LocalDate startDate,LocalDate endDate,String role); 
}
