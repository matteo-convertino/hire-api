package com.convertino.hire.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPositionResponseDTO {
    private long id;
    private String title;
    private String description;
    private String lastMessage;
    private String evaluationCriteria;
    private List<SkillResponseDTO> skills;
    private long userId;
}