package com.example.utnphones.controller;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.*;
import com.example.utnphones.service.AccountService;
import com.example.utnphones.service.BillService;
import com.example.utnphones.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final AccountService accountService;
    private final BillService billService;
    private final CallService callService;

    @Autowired
    public ClientController(AccountService accountService, BillService billService, CallService callService) {
        this.accountService = accountService;
        this.billService = billService;
        this.callService = callService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(Authentication auth, @PathVariable Long id) throws NotFoundEntityException {
        Client client = accountService.getClientById(id);
        User loggedUser = (User) auth.getPrincipal();

        if (!client.getUser().getUsername().equals(loggedUser.getUsername())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(client);
    }

    @GetMapping("/{id}/bills")
    public ResponseEntity<Page<Bill>> getBillsOfClient(
            Authentication auth,
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam String from,
            @RequestParam String until) throws NotFoundEntityException {
        Pageable pageable = PageRequest.of(page, pageSize);

        Client client = accountService.getClientById(id);
        User loggedUser = (User) auth.getPrincipal();

        if (!client.getUser().getUsername().equals(loggedUser.getUsername())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        LocalDate dateFrom = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate dateUntil = LocalDate.parse(until, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Page<Bill> bills = billService.getBillsByClient(pageable, client, dateFrom, dateUntil);

        return ResponseEntity.ok(bills);
    }

    @GetMapping(value = "/{phoneOrigin}/calls")
    public ResponseEntity<Page<Call>> getCallByPhoneOrigin(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam String from,
            @RequestParam String until,
            @PathVariable String phoneOrigin,
            Authentication auth) throws NotFoundEntityException {
        Pageable pageable = PageRequest.of(page, pageSize);

        Client client = accountService.getClientByPhoneNumber(phoneOrigin);
        User loggedUser = (User) auth.getPrincipal();
        if(!client.getUser().getUsername().equals(loggedUser.getUsername())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        LocalDateTime dateFrom = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime dateUntil = LocalDateTime.parse(until, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Page<Call> calls = callService.getCallsMadeByNumber(pageable, client, dateFrom, dateUntil);

        return ResponseEntity.ok(calls);
    }
}
