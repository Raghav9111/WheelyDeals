package com.wheelyDeals.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wheelyDeals.entities.VehicleRequest;

@Repository
public interface VehicleRequestRepo extends JpaRepository<VehicleRequest,Integer>
{
	Optional<List<VehicleRequest>> findAllByRequestStatus(String requestStatus);
}
