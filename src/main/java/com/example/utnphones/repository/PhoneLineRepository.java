package com.example.utnphones.repository;

import com.example.utnphones.model.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine, Long> {
    public Optional<PhoneLine> findByPhoneNumber(String phoneNumber);
}
