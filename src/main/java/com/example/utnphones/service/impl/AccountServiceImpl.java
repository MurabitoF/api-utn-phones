package com.example.utnphones.service.impl;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Account;
import com.example.utnphones.model.Client;
import com.example.utnphones.model.Employee;
import com.example.utnphones.repository.ClientRepository;
import com.example.utnphones.repository.EmployeeRepository;
import com.example.utnphones.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class AccountServiceImpl implements AccountService {

    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AccountServiceImpl(EmployeeRepository employeeRepository, ClientRepository clientRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Employee getEmployeeById(Long id) throws NotFoundEntityException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Employee"));
    }

    @Override
    public Client getClientById(Long id) throws NotFoundEntityException {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Client"));
    }

    @Override
    public Account saveNewEmployee(Account account) {
        if (account instanceof Employee){
            Employee newEmployee = modelMapper.map(account, Employee.class);
            return employeeRepository.save(newEmployee);
        } else {
            Client newClient = modelMapper.map(account, Client.class);
            return clientRepository.save(newClient);
        }
    }
}
