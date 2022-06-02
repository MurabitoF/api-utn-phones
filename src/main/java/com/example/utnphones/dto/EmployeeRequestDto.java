package com.example.utnphones.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("employeeDto")
public class EmployeeRequestDto extends AccountRequestDto{

    @NotBlank(message = "area is required")
    @Size(min = 2, message = "area must have at least 2 characters")
    private String area;

}
