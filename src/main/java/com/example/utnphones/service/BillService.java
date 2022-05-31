package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Bill;
import com.example.utnphones.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillService {

    public Page<Bill> getAllBills(Pageable pageable);
    public Page<Bill> getBillsByClient(Pageable pageable, Client client);
    public Bill getBillById(Long id) throws NotFoundEntityException;
}
