package com.convertino.hire.mapper;

import com.convertino.hire.dto.request.ReportRequestDTO;
import com.convertino.hire.dto.request.SkillRequestDTO;
import com.convertino.hire.dto.response.ReportResponseDTO;
import com.convertino.hire.dto.response.SkillResponseDTO;
import com.convertino.hire.model.Report;
import com.convertino.hire.model.Skill;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    @InheritInverseConfiguration
    @Mapping(source = "interview.user.id", target = "interview.userId")
    @Mapping(source = "interview.jobPosition.id", target = "interview.jobPositionId")
    ReportResponseDTO mapToDTO(Report report);

    Report mapToReport(ReportRequestDTO reportRequestDTO);

    List<ReportResponseDTO> mapToDTO(List<Report> reports);
}
