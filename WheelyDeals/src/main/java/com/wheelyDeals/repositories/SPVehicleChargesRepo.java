package com.wheelyDeals.repositories;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wheelyDeals.entities.ServiceProvider;
import com.wheelyDeals.entities.ServiceProviderCharges;
import com.wheelyDeals.entities.VehicleMaster;

public interface SPVehicleChargesRepo extends JpaRepository<ServiceProviderCharges, Integer> {

	Optional<ServiceProviderCharges> findByVehicleMasterAndServiceProvider(VehicleMaster vm,ServiceProvider sp);
}
