package com.example.utnphones.repository;

import com.example.utnphones.model.Bill;
import com.example.utnphones.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    Page<Bill> findAllByClientAndBillDateBetween(Pageable pageable, Client client, LocalDate from, LocalDate until);
}
