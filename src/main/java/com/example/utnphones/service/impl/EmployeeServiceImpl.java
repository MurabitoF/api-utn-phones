package com.example.utnphones.service.impl;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Employee;
import com.example.utnphones.repository.EmployeeRepository;
import com.example.utnphones.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Page<Employee> getAllEmployee(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Employee getEmployeeById(Long id) throws NotFoundEntityException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Employee"));
    }

    @Override
    public Employee saveNewEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
