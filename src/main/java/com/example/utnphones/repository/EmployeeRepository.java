package com.example.utnphones.repository;

import com.example.utnphones.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByDeleteAtNull(Pageable pageable);

    Optional<Employee> findByAccountIdAndDeleteAtNull(Long id);
}
