package com.example.utnphones.repository;

import com.example.utnphones.model.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine, Long> {
}
