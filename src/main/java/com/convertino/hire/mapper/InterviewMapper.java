package com.convertino.hire.mapper;

import com.convertino.hire.dto.request.InterviewRequestDTO;
import com.convertino.hire.dto.request.SkillRequestDTO;
import com.convertino.hire.dto.response.InterviewResponseDTO;
import com.convertino.hire.dto.response.SkillResponseDTO;
import com.convertino.hire.model.Interview;
import com.convertino.hire.model.Skill;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InterviewMapper {
    @InheritInverseConfiguration
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "jobPosition.id", target = "jobPositionId")
    InterviewResponseDTO mapToDTO(Interview interview);

    Interview mapToInterview(InterviewRequestDTO interviewRequestDTO);

    List<InterviewResponseDTO> mapToDTO(List<Interview> interviews);
}
