package com.example.utnphones.repository;

import com.example.utnphones.model.CallFee;
import com.example.utnphones.model.CallFeeRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallFeeRangeRepository extends JpaRepository<CallFeeRange, Long> {
    public List<CallFeeRange> findByCallFee(CallFee callFee);
}
