package com.example.utnphones.controller;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Bill;
import com.example.utnphones.model.Client;
import com.example.utnphones.service.AccountService;
import com.example.utnphones.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;
    private final AccountService accountService;

    @Autowired
    public BillController(BillService billService, AccountService accountService) {
        this.billService = billService;
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<Bill>> getAllBills(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Bill> bills = billService.getAllBills(pageable);

        return ResponseEntity.ok(bills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) throws NotFoundEntityException {
        return ResponseEntity.ok(billService.getBillById(id));
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Page<Bill>> getBillsOfClient(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam String from,
            @RequestParam String until) throws NotFoundEntityException {
        Pageable pageable = PageRequest.of(page, pageSize);

        Client client = accountService.getClientById(id);
        LocalDate dateFrom = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate dateUntil = LocalDate.parse(until, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Page<Bill> bills = billService.getBillsByClient(pageable, client, dateFrom, dateUntil);

        return ResponseEntity.ok(bills);
    }
}
