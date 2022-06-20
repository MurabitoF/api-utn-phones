package com.example.utnphones.dto;

import com.example.utnphones.model.Client;
import com.example.utnphones.model.Employee;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "@type")
@JsonSubTypes(
        {@JsonSubTypes.Type(value = ClientRequestDto.class, name = "clientDto"),
                @JsonSubTypes.Type(value = EmployeeRequestDto.class, name = "employeeDto")
        })
public class AccountRequestDto {

    @NotBlank(message = "dni is required")
    @Size(min = 7, message = "dni must have at least 7 characters")
    private String dni;

    @NotBlank(message = "firstName is required")
    @Size(min = 3, message = "firstName must have at least 3 characters")
    private String firstName;

    @NotBlank(message = "surname is required")
    @Size(min = 3, message = "surname must have at least 3 characters")
    private String surname;

    @NotNull(message = "cityId is required")
    @Positive(message = "cityId must be greater than 0")
    private Long cityId;

    @NotBlank(message = "username is required")
    private String username;

}
