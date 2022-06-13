package com.example.utnphones.service;

import com.example.utnphones.exception.EntityExitstExeption;
import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Account;
import com.example.utnphones.model.Client;
import com.example.utnphones.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountService {
    public Page<Employee> getAllEmployees(Pageable pageable);
    public Page<Client> getAllClients(Pageable pageable);
    public Account getAccountById(Long id) throws NotFoundEntityException;
    public Employee getEmployeeById(Long id) throws NotFoundEntityException;
    public Client getClientById(Long id) throws NotFoundEntityException;
    public Account saveNewAccount(Account account);
    public Account updateAccount(Long id, Account account) throws NotFoundEntityException, EntityExitstExeption;
    public void deleteAccount(Long id) throws NotFoundEntityException;
    public Client getClientByPhoneNumber(String phoneNumber) throws NotFoundEntityException;
    public Boolean phoneNumberExist(String phoneNumber);
}
