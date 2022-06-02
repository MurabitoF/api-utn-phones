package com.example.utnphones.controller;

import com.example.utnphones.dto.AccountRequestDto;
import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Account;
import com.example.utnphones.model.Client;
import com.example.utnphones.model.Employee;
import com.example.utnphones.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/employees/")
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Employee> employees = accountService.getAllEmployees(pageable);

        if(!employees.hasContent()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws NotFoundEntityException {
        return ResponseEntity.ok(accountService.getEmployeeById(id));
    }

    @GetMapping("/clients/")
    public ResponseEntity<Page<Client>> getAllClients(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Client> clients = accountService.getAllClients(pageable);

        if(!clients.hasContent()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(clients);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) throws NotFoundEntityException {
        return ResponseEntity.ok(accountService.getClientById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Account> saveNewAccount(@RequestBody final AccountRequestDto account){
        return ResponseEntity.ok(new Client());
    }

}
