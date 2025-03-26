package com.convertino.hire.mapper;

import com.convertino.hire.dto.request.ReportRequestDTO;
import com.convertino.hire.dto.request.SkillRequestDTO;
import com.convertino.hire.dto.response.ReportResponseDTO;
import com.convertino.hire.dto.response.SkillResponseDTO;
import com.convertino.hire.model.Report;
import com.convertino.hire.model.Skill;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    @InheritInverseConfiguration
    ReportResponseDTO mapToDTO(Report report);

    Report mapToReport(ReportRequestDTO reportRequestDTO);

    List<ReportResponseDTO> mapToDTO(List<Report> reports);
}
