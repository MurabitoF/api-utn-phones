package com.example.utnphones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallFeeRangeRequestDto {

    @NotBlank(message = "startAt is required")
    private String startAt;

    @NotBlank(message = "endAt is required")
    private String endAt;

    @NotNull(message = "price is required")
    @Positive(message = "price must be greater than 0")
    private Double price;
}
