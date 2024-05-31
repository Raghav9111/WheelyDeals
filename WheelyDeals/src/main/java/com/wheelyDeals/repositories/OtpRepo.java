package com.wheelyDeals.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wheelyDeals.entities.Otp;
import com.wheelyDeals.entities.User;

@Repository
public interface OtpRepo extends JpaRepository<Otp, Integer> {

	Optional<Otp> findByUser(User user);
}
