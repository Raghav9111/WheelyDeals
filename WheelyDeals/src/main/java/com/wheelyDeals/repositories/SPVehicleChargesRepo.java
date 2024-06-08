package com.wheelyDeals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wheelyDeals.entities.ServiceProviderCharges;

public interface SPVehicleChargesRepo extends JpaRepository<ServiceProviderCharges, Integer> {

}
