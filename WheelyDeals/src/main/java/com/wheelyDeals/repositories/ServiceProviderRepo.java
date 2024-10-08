package com.wheelyDeals.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wheelyDeals.entities.ServiceProvider;
import com.wheelyDeals.entities.ServiceProviderVehicle;
import com.wheelyDeals.entities.User;
import com.wheelyDeals.entities.VehicleMaster;

@Repository
public interface ServiceProviderRepo extends JpaRepository<ServiceProvider,Integer>
{	
	@Query("select sp from ServiceProvider sp where sp.role=?1 and sp.isblock=?2")
	Optional<List<ServiceProvider>> findByRoleAndIsblock(String role , Boolean status);
}
