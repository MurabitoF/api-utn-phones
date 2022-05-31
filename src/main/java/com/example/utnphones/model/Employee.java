package com.example.utnphones.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("employee")
@Table(name = "employees")
public class Employee extends Account {

    @Column(name = "employee_area")
    private String employeeArea;
}
