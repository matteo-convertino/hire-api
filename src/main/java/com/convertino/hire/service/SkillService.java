package com.convertino.hire.service;


import com.convertino.hire.dto.request.SkillRequestDTO;
import com.convertino.hire.dto.request.SkillUpdateRequestDTO;
import com.convertino.hire.dto.response.SkillResponseDTO;
import com.convertino.hire.model.Interview;
import com.convertino.hire.model.Skill;

import java.util.List;

public interface SkillService {
    Skill findEntityById(long id);
    SkillResponseDTO findById(long id);
    List<SkillResponseDTO> findAllByJobPositionId(long jobPositionId);
    SkillResponseDTO save(SkillRequestDTO skillRequestDTO);
    SkillResponseDTO update(long id, SkillUpdateRequestDTO skillUpdateRequestDTO);
    SkillResponseDTO delete(long id);
}