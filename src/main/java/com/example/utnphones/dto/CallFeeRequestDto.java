package com.example.utnphones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CallFeeRequestDto {

    @NotNull(message = "startAt date is required")
    private String startAt;

    @NotNull(message = "endAt date is required")
    private String endAt;

    @NotNull(message = "price date is required")
    @Min(value = 0, message = "the price must be greater than 0")
    private Double price;

    @NotNull(message = "cityOrg is required")
    private Long cityOrigin;

    @NotNull(message = "cityDest is required")
    private Long cityDestination;
}
