package com.convertino.hire.service;


import com.convertino.hire.dto.request.ReportRequestDTO;
import com.convertino.hire.dto.request.SkillRequestDTO;
import com.convertino.hire.dto.request.SkillUpdateRequestDTO;
import com.convertino.hire.dto.response.ReportResponseDTO;
import com.convertino.hire.dto.response.SkillResponseDTO;
import com.convertino.hire.model.User;

import java.util.List;

public interface ReportService {
    ReportResponseDTO save(User user, ReportRequestDTO reportRequestDTO);
}