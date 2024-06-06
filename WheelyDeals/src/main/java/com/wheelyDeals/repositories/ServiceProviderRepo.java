package com.wheelyDeals.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wheelyDeals.entities.ServiceProvider;

@Repository
public interface ServiceProviderRepo extends JpaRepository<ServiceProvider,Integer>
{
	@Query
	Optional<List<ServiceProvider>> findByRoleAndIsblock(String role , Boolean status);
}
