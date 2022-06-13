package com.example.utnphones.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonTypeName("clientDto")
public class ClientRequestDto extends AccountRequestDto{

    @NotBlank(message = "phoneNumber is required")
    @Size(min = 10, max = 10, message = "Phone number must have 10 characters")
    private String phoneNumber;

}
