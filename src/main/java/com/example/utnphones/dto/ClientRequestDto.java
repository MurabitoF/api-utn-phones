package com.example.utnphones.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("clientDto")
public class ClientRequestDto extends AccountRequestDto{

    @NotNull(message = "phoneLine is required")
    @Positive(message = "phoneLine must be greater than 0")
    private Long phoneLineId;
}
