package com.convertino.hire.service;


import com.convertino.hire.dto.request.InterviewRequestDTO;
import com.convertino.hire.dto.request.SkillRequestDTO;
import com.convertino.hire.dto.request.SkillUpdateRequestDTO;
import com.convertino.hire.dto.response.InterviewResponseDTO;
import com.convertino.hire.dto.response.SkillResponseDTO;
import com.convertino.hire.model.Interview;

import java.util.List;

public interface InterviewService {
    Interview findEntityById(long id);
    InterviewResponseDTO findById(long id);
    List<InterviewResponseDTO> findAllByJobPositionId(long jobPositionId);
    List<InterviewResponseDTO> findAllByUser();
    InterviewResponseDTO save(InterviewRequestDTO interviewRequestDTO);
}