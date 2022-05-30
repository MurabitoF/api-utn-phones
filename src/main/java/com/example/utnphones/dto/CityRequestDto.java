package com.example.utnphones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityRequestDto {

    @NotBlank(message = "City name is required")
    @Size(min = 5, message = "City name must have at least 5 characters")
    private String cityName;

    @NotBlank(message = "Area code is required")
    @Size(min = 2, message = "Area code must have al least 2 characters")
    private String areaCode;

    @NotNull(message = "Province id is required")
    private Long provinceId;
}
