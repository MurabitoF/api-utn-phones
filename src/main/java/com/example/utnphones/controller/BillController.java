package com.example.utnphones.controller;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Bill;
import com.example.utnphones.model.Client;
import com.example.utnphones.service.BillService;
import com.example.utnphones.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;
    private final ClientService clientService;

    @Autowired
    public BillController(BillService billService, ClientService clientService) {
        this.billService = billService;
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<Bill>> getAllBills(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Bill> bills = billService.getAllBills(pageable);

        if(!bills.hasContent()){
            return ResponseEntity.noContent().build();
        }

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
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws NotFoundEntityException {
        Pageable pageable = PageRequest.of(page, pageSize);

        Client client = clientService.getClientById(id);

        Page<Bill> bills = billService.getBillsByClient(pageable, client);

        if (!bills.hasContent()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(bills);
    }
}
