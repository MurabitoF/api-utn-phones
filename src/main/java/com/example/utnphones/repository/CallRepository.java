package com.example.utnphones.repository;

import com.example.utnphones.model.Call;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {
    Page<Call> findAllByPhoneOrigen(Pageable pageable, String phoneOrigen);
}
