package com.example.utnphones.controller;

import com.example.utnphones.dto.AccountRequestDto;
import com.example.utnphones.dto.ClientRequestDto;
import com.example.utnphones.dto.EmployeeRequestDto;
import com.example.utnphones.exception.EntityExitstExeption;
import com.example.utnphones.exception.MappingException;
import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.*;
import com.example.utnphones.service.AccountService;
import com.example.utnphones.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final CityService cityService;


    @Autowired
    public AccountController(AccountService accountService, CityService cityService) {
        this.accountService = accountService;
        this.cityService = cityService;
    }

    @GetMapping("/employees/")
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Employee> employees = accountService.getAllEmployees(pageable);

//        if(!employees.hasContent()){
//            return ResponseEntity.noContent().build();
//        }

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

//        if(!clients.hasContent()){
//            return ResponseEntity.noContent().build();
//        }

        return ResponseEntity.ok(clients);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) throws NotFoundEntityException {
        return ResponseEntity.ok(accountService.getClientById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Account> saveNewAccount(@Valid @RequestBody final AccountRequestDto accountRequest) throws NotFoundEntityException {
        Account newAccount = this.convertToEntity(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.saveNewAccount(newAccount));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@Valid @RequestBody AccountRequestDto accountRequest, @PathVariable Long id) throws NotFoundEntityException, EntityExitstExeption {
        Account account = this.convertToEntity(accountRequest);
        return ResponseEntity.ok(accountService.updateAccount(id, account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long id) throws NotFoundEntityException {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    private Account convertToEntity(AccountRequestDto accountRequest) throws NotFoundEntityException {
        City city = cityService.getCityById(accountRequest.getCityId());

        if(accountRequest instanceof EmployeeRequestDto){
            return Employee.builder()
                    .firstName(accountRequest.getFirstName())
                    .surname(accountRequest.getSurname())
                    .dni(accountRequest.getDni())
                    .city(city)
                    .employeeArea(((EmployeeRequestDto) accountRequest).getArea())
                    .build();
        } else {

            return Client.builder()
                    .firstName(accountRequest.getFirstName())
                    .surname(accountRequest.getSurname())
                    .dni(accountRequest.getDni())
                    .city(city)
                    .phoneNumber(((ClientRequestDto) accountRequest).getPhoneNumber())
                    .build();
        }
    }
}
