package com.convertino.hire.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillRequestDTO {
    @NotBlank(message = "description cannot be blank")
    private String description;

    @NotNull(message = "jobPosition cannot be blank")
    private Integer jobPositionId;
}