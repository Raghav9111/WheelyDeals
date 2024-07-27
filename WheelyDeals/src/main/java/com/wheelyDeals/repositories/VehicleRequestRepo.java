package com.wheelyDeals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wheelyDeals.entities.VehicleRequest;

@Repository
public interface VehicleRequestRepo extends JpaRepository<VehicleRequest,Integer>
{
	
}
