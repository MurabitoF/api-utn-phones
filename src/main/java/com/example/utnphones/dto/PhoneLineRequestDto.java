package com.example.utnphones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneLineRequestDto {

    @NotBlank
    @Size(min = 10, max = 10, message = "phoneNumber must have 10 characters")
    private String phoneNumber;
}
