package com.convertino.hire.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPositionRequestDTO {

    @NotBlank(message = "title cannot be blank")
    @Size(min = 1, max = 255, message = "title cannot be less than 1 character or more than 255 characters long")
    private String title;

    @NotBlank(message = "description cannot be blank")
    private String description;

    @Size(min = 1, message = "lastMessage cannot be blank")
    private String lastMessage;

    @Size(min = 1, message = "evaluationCriteria cannot be blank")
    private String evaluationCriteria;
}