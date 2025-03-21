package com.convertino.hire.mapper;

import com.convertino.hire.dto.request.SkillRequestDTO;
import com.convertino.hire.dto.response.SkillResponseDTO;
import com.convertino.hire.model.Skill;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    @InheritInverseConfiguration
    SkillResponseDTO mapToDTO(Skill skill);

    Skill mapToSkill(SkillRequestDTO skillRequestDTO);

    List<SkillResponseDTO> mapToDTO(List<Skill> skills);
}
