package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Bill;
import com.example.utnphones.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface BillService {

    public Page<Bill> getAllBills(Pageable pageable);
    public Page<Bill> getBillsByClient(Pageable pageable, Client client, LocalDateTime from, LocalDateTime until);
    public Bill getBillById(Long id) throws NotFoundEntityException;
}
