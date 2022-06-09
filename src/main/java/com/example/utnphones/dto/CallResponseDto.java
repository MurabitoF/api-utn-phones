package com.example.utnphones.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallResponseDto {

    private Long callId;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime callDate;
    private Integer duration;
    private String phoneOrigin;
    private String phoneDestination;
    private Double total;
    private String cityOrigin;
    private String cityDestination;
}
