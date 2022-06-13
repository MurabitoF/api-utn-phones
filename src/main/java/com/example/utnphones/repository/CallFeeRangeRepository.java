package com.example.utnphones.repository;

import com.example.utnphones.model.CallFee;
import com.example.utnphones.model.CallFeeRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CallFeeRangeRepository extends JpaRepository<CallFeeRange, Long> {
    public List<CallFeeRange> findAllByCallFee(CallFee callFee);
    @Query(value = "select * from calls_fees_ranges where call_fee_id = ?1 and (start_at between ?2 and ?3 or end_at between ?2 and ?3)", nativeQuery = true)
    public List<CallFeeRange> findAllByCallFeeAndStartAtIsBetweenOrEndAtIsBetween(CallFee callFee, LocalTime startAt, LocalTime endAt);
}
