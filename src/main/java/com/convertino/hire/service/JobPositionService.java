package com.convertino.hire.service;


import com.convertino.hire.dto.request.JobPositionRequestDTO;
import com.convertino.hire.dto.response.JobPositionResponseDTO;
import com.convertino.hire.model.JobPosition;

import java.util.List;

public interface JobPositionService {
    List<JobPositionResponseDTO> findAll();
    JobPositionResponseDTO findById(long id);
    JobPosition findEntityById(long id);
    JobPositionResponseDTO save(JobPositionRequestDTO jobPositionRequestDTO);
    JobPositionResponseDTO update(long id, JobPositionRequestDTO jobPositionRequestDTO);
    JobPositionResponseDTO delete(long id);
    void checkOwnership(JobPosition jobPosition);
}