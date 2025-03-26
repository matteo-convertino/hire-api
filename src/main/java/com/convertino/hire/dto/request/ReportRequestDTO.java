package com.convertino.hire.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDTO {
    @Min(value = 1, message = "value cannot be lower than 1")
    @Max(value = 10, message = "value cannot be greater than 10")
    private int value;

    @NotNull(message = "interviewId cannot be null")
    private Long interviewId;

    @NotNull(message = "skillId cannot be null")
    private Long skillId;
}