package com.example.utnphones.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CallRequestDto {

    @NotBlank(message = "phoneOrg is required")
    @Size(min = 10, message = "phoneOrg must have 10 characters")
    @Size(max = 10, message = "phoneOrg must have 10 characters")
    private String origin;

    @NotBlank(message = "startAt date is required")
    @Size(min = 10, message = "phoneDest must have 10 characters")
    @Size(max = 10, message = "phoneDest must have 10 characters")
    private String destination;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @NotNull(message = "callDate is required")
    private String datetime;

    @NotNull(message = "duration is required")
    @Min(value = 1, message = "the duration must be greater than 1")
    private Integer duration;
}
