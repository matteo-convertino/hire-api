package com.convertino.hire.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewRequestDTO {
    @NotNull(message = "jobPosition cannot be blank")
    private Long jobPositionId;
}