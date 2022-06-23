package com.example.utnphones.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonTypeName("employee")
@Table(name = "employees")
@PrimaryKeyJoinColumn(name = "employee_id")
public class Employee extends Account {

    @Column(name = "employee_area")
    private String employeeArea;

//    public String getEmployeeArea() {
//        return employeeArea;
//    }
//
//    public void setEmployeeArea(String employeeArea) {
//        this.employeeArea = employeeArea;
//    }
}
