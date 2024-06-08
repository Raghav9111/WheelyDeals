package com.wheelyDeals.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wheelyDeals.entities.ServiceProvider;
import com.wheelyDeals.entities.ServiceProviderVehicle;

public interface SPVehicleRepo extends JpaRepository<ServiceProviderVehicle, Integer>
{
	@Query("select v from ServiceProviderVehicle v where status !=?1 and serviceProvider=?2")
	Optional<List<ServiceProviderVehicle>> findByStatusAndServiceProvider(String status, ServiceProvider sp);

}
