package com.example.utnphones.repository;

import com.example.utnphones.model.CallFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallFeeRepository extends JpaRepository<CallFee, Long> {
}
