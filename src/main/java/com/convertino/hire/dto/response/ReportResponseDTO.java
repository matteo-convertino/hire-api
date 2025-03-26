package com.convertino.hire.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponseDTO {
    private long id;
    private int value;
    private InterviewResponseDTO interview;
    private SkillResponseDTO skill;
}