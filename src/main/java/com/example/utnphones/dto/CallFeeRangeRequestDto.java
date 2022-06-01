package com.example.utnphones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallFeeRangeRequestDto {

    private String startAt;
    private String endAt;
}
