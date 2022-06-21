package com.example.utnphones.controller;

import com.example.utnphones.dto.AccountRequestDto;
import com.example.utnphones.dto.ClientRequestDto;
import com.example.utnphones.dto.EmployeeRequestDto;
import com.example.utnphones.exception.EntityExitstExeption;
import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.*;
import com.example.utnphones.service.AccountService;
import com.example.utnphones.service.CityService;
import com.example.utnphones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final CityService cityService;
    private final UserService userService;


    @Autowired
    public AccountController(AccountService accountService, CityService cityService, UserService userService) {
        this.accountService = accountService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @GetMapping("/employees/")
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Employee> employees = accountService.getAllEmployees(pageable);

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws NotFoundEntityException {
        return ResponseEntity.ok(accountService.getEmployeeById(id));
    }

    @GetMapping("/clients/")
    public ResponseEntity<Page<Client>> getAllClients(
            Authentication auth,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        User loggedUser = (User) auth.getPrincipal();

        if(!loggedUser.getRole().equals(Role.EMPLOYEE)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Page<Client> clients = accountService.getAllClients(pageable);

        return ResponseEntity.ok(clients);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(Authentication auth, @PathVariable Long id) throws NotFoundEntityException {
        Client client = accountService.getClientById(id);
        User loggedUser = (User) auth.getPrincipal();

        if (loggedUser.getRole().equals(Role.CLIENT) && !client.getUser().getUsername().equals(loggedUser.getUsername())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

            return ResponseEntity.ok(client);
    }

    @PostMapping("/")
    public ResponseEntity<Account> saveNewAccount(@Valid @RequestBody final AccountRequestDto accountRequest) throws NotFoundEntityException, EntityExitstExeption {
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
        User user = (User) userService.loadUserByUsername(accountRequest.getUsername());
        if(accountRequest instanceof EmployeeRequestDto){
            return Employee.builder()
                    .firstName(accountRequest.getFirstName())
                    .surname(accountRequest.getSurname())
                    .dni(accountRequest.getDni())
                    .city(city)
                    .user(user)
                    .employeeArea(((EmployeeRequestDto) accountRequest).getArea())
                    .build();
        } else {
            return Client.builder()
                    .firstName(accountRequest.getFirstName())
                    .surname(accountRequest.getSurname())
                    .dni(accountRequest.getDni())
                    .city(city)
                    .user(user)
                    .phoneNumber(((ClientRequestDto) accountRequest).getPhoneNumber())
                    .build();
        }
    }
}
