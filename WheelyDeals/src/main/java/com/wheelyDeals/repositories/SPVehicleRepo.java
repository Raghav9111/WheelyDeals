package com.wheelyDeals.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wheelyDeals.entities.ServiceProvider;
import com.wheelyDeals.entities.ServiceProviderVehicle;
import com.wheelyDeals.entities.VehicleMaster;

@Repository
public interface SPVehicleRepo extends JpaRepository<ServiceProviderVehicle, Integer>
{
	@Query("select v from ServiceProviderVehicle v where status !=?1 and serviceProvider=?2")
	Optional<List<ServiceProviderVehicle>> findByStatusAndServiceProvider(String status, ServiceProvider sp, Pageable pageable);
	
	@Query("select COUNT(v) from ServiceProviderVehicle v where status !=?1")
    Optional<Long> countByStatus(String status);
	
	Optional<List<ServiceProviderVehicle>> findByVehicleMaster(VehicleMaster vm);

	@Query("select v from ServiceProviderVehicle v where serviceProvider =?1 and fuelType=?2 and vehicleMaster=?3 and status != ?4")
	Optional<List<ServiceProviderVehicle>> findByServiceProviderAndFuelTypeAndVehicleMasterAndStatus(ServiceProvider sp, String fuelType,
			VehicleMaster vehicleMaster,String Status);
	
	@Query("SELECT spv FROM ServiceProviderVehicle spv WHERE serviceProvider = ?1 AND vehicleMaster = ?2  AND spv NOT IN (SELECT b.spv FROM Booking b WHERE b.vehicleMaster = ?2 AND (b.tripStartDate < ?3 AND b.tripEndDate > ?4))")
	Optional<List<ServiceProviderVehicle>> findAvailableServiceProviderVehicles(ServiceProvider sp,VehicleMaster vm,LocalDate endDate, LocalDate startDate);
}