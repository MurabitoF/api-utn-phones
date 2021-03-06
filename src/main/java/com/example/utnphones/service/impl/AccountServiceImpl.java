package com.example.utnphones.service.impl;

import com.example.utnphones.exception.EntityExitstExeption;
import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Account;
import com.example.utnphones.model.Client;
import com.example.utnphones.model.Employee;
import com.example.utnphones.repository.AccountRepository;
import com.example.utnphones.repository.ClientRepository;
import com.example.utnphones.repository.EmployeeRepository;
import com.example.utnphones.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, EmployeeRepository employeeRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAllByDeleteAtNull(pageable);
    }

    @Override
    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAllByDeleteAtNull(pageable);
    }

    @Override
    public Account getAccountById(Long id) throws NotFoundEntityException {
        return accountRepository.findByAccountIdAndDeleteAtNull(id)
                .orElseThrow(() -> new NotFoundEntityException("Account"));
    }

    @Override
    public Employee getEmployeeById(Long id) throws NotFoundEntityException {
        return employeeRepository.findByAccountIdAndDeleteAtNull(id)
                .orElseThrow(() -> new NotFoundEntityException("Employee"));
    }

    @Override
    public Client getClientById(Long id) throws NotFoundEntityException {
        return clientRepository.findByAccountIdAndDeleteAtNull(id)
                .orElseThrow(() -> new NotFoundEntityException("Client"));
    }

    @Override
    public Account saveNewAccount(Account account) throws EntityExitstExeption {
        if (account instanceof Employee){
            Employee newEmployee = (Employee) account;
            return employeeRepository.save(newEmployee);
        } else {
            Client newClient = (Client) account;
            if (phoneNumberExist(newClient.getPhoneNumber())){
                    throw new EntityExitstExeption("Phone number");
            }

            return clientRepository.save(newClient);
        }
    }

    @Override
    public Account updateAccount(Long id, Account account) throws NotFoundEntityException, EntityExitstExeption {
        Account updatedAccount = this.getAccountById(id);

        if (!StringUtils.isBlank(account.getFirstName()) && !updatedAccount.getFirstName().equals(account.getFirstName())){
            updatedAccount.setFirstName(account.getFirstName());
        }
        if (!StringUtils.isBlank(account.getSurname()) && !updatedAccount.getSurname().equals(account.getSurname())){
            updatedAccount.setSurname(account.getSurname());
        }
        if (!Objects.isNull(account.getCity()) && !updatedAccount.getCity().equals(account.getCity())){
            updatedAccount.setCity(account.getCity());
        }

        if(account instanceof Employee){
            Employee employee = (Employee) account;
            Employee updatedEmployee = (Employee) updatedAccount;

            if (!StringUtils.isBlank(employee.getEmployeeArea()) && !updatedEmployee.getEmployeeArea().equals(employee.getEmployeeArea())){
                updatedEmployee.setEmployeeArea(employee.getEmployeeArea());
            }

            return employeeRepository.save(updatedEmployee);
        }else{
            Client client = (Client) account;
            Client updatedClient = (Client) updatedAccount;

            if (!Objects.equals(updatedClient.getPhoneNumber(), client.getPhoneNumber())){
                if (phoneNumberExist(client.getPhoneNumber())){
                    throw new EntityExitstExeption("Phone number");
                }
                updatedClient.setPhoneNumber(client.getPhoneNumber());
            }

            return clientRepository.save(updatedClient);
        }
    }

    @Override
    public void deleteAccount(Long id) throws NotFoundEntityException {
        Account deletedAccount = this.getAccountById(id);
        deletedAccount.setDeleteAt(LocalDateTime.now());
        accountRepository.save(deletedAccount);
    }

    @Override
    public Client getClientByPhoneNumber(String phoneNumber) throws NotFoundEntityException {
        return clientRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundEntityException("Client"));
    }

    @Override
    public Boolean phoneNumberExist(String phoneNumber) {
        Optional<Client> client = clientRepository.findByPhoneNumber(phoneNumber);
        return client.isPresent();
    }
}
