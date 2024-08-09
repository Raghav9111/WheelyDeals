package com.wheelyDeals.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wheelyDeals.entities.Booking;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer >
{

	@Query("SELECT b FROM Booking b WHERE " +
	           "((:startDate < b.tripEndDate) OR " +
	           "(:startDate BETWEEN b.tripStartDate AND b.tripEndDate) OR " +
	           "(:endDate BETWEEN b.tripStartDate AND b.tripEndDate) OR " +
	           "(b.tripStartDate BETWEEN :startDate AND :endDate) OR " +
	           "(b.tripEndDate BETWEEN :startDate AND :endDate))")
	Optional<List<Booking>> findByTripStartDateAndTripEndDate(LocalDate startDate, LocalDate endDate);
	
}
