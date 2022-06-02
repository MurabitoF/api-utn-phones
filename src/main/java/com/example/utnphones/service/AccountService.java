package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Account;
import com.example.utnphones.model.Client;
import com.example.utnphones.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    public Page<Employee> getAllEmployees(Pageable pageable);
    public Page<Client> getAllClients(Pageable pageable);
    public Employee getEmployeeById(Long id) throws NotFoundEntityException;
    public Client getClientById(Long id) throws NotFoundEntityException;
    public Account saveNewAccount(Account account);
}