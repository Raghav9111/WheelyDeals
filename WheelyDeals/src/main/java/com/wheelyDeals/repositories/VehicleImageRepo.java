package com.wheelyDeals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wheelyDeals.entities.VehicleImages;

@Repository
public interface VehicleImageRepo extends JpaRepository<VehicleImages, Integer>{

}
