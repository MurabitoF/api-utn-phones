package com.example.utnphones.repository;

import com.example.utnphones.model.Call;
import com.example.utnphones.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {
    Page<Call> findAllByPhoneOriginAndCallDateBetween(Pageable pageable, Client phoneOrigen, LocalDateTime from, LocalDateTime until);
}
