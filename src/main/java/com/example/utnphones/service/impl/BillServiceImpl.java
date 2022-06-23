package com.example.utnphones.service.impl;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Bill;
import com.example.utnphones.model.Client;
import com.example.utnphones.repository.BillRepository;
import com.example.utnphones.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Page<Bill> getAllBills(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    @Override
    public Page<Bill> getBillsByClient(Pageable pageable, Client client, LocalDate from, LocalDate until) {
        return billRepository.findAllByClientAndBillDateBetween(pageable, client, from, until);
    }

    @Override
    public Bill getBillById(Long id) throws NotFoundEntityException {
        return billRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Bill"));
    }
}
