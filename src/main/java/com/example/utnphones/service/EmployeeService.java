package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    public Page<Employee> getAllEmployee(Pageable pageable);
    public Employee getEmployeeById(Long id) throws NotFoundEntityException;
    public Employee saveNewEmployee(Employee employee);
}
