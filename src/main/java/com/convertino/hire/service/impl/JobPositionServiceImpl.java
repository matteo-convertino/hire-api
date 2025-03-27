package com.convertino.hire.service.impl;


import com.convertino.hire.dto.request.JobPositionRequestDTO;
import com.convertino.hire.dto.response.JobPositionResponseDTO;
import com.convertino.hire.exceptions.entity.*;
import com.convertino.hire.mapper.JobPositionMapper;
import com.convertino.hire.model.JobPosition;
import com.convertino.hire.model.User;
import com.convertino.hire.repository.JobPositionRepository;
import com.convertino.hire.service.JobPositionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class JobPositionServiceImpl implements JobPositionService {
    private final JobPositionMapper jobPositionMapper;
    private final JobPositionRepository jobPositionRepository;

    @Override
    public List<JobPositionResponseDTO> findAll() {
        return jobPositionMapper.mapToDTO(jobPositionRepository.findAll());
    }

    @Override
    public List<JobPositionResponseDTO> findAllByUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return jobPositionMapper.mapToDTO(jobPositionRepository.findAllByUser(user));
    }

    @Override
    public JobPosition findEntityById(long id) {
        return jobPositionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("jobPosition", "id", id));
    }

    @Override
    public JobPositionResponseDTO findById(long id) {
        return jobPositionMapper.mapToDTO(findEntityById(id));
    }

    @Override
    public JobPositionResponseDTO save(JobPositionRequestDTO jobPositionRequestDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        JobPosition jobPosition = jobPositionMapper.mapToJobPosition(jobPositionRequestDTO);

        jobPosition.setUser(user);

        try {
            jobPosition = jobPositionRepository.save(jobPosition);
        } catch (Exception e) {
            throw new EntityCreationException("jobPosition");
        }

        return jobPositionMapper.mapToDTO(jobPosition);
    }

    @Override
    public JobPositionResponseDTO update(long id, JobPositionRequestDTO jobPositionRequestDTO) {
        JobPosition jobPosition = findEntityById(id);

        checkOwnership(jobPosition);

        jobPosition.setTitle(jobPositionRequestDTO.getTitle());
        jobPosition.setDescription(jobPositionRequestDTO.getDescription());
        jobPosition.setLastMessage(jobPositionRequestDTO.getLastMessage());
        jobPosition.setEvaluationCriteria(jobPositionRequestDTO.getEvaluationCriteria());

        try {
            jobPosition = jobPositionRepository.save(jobPosition);
        } catch (Exception e) {
            throw new EntityUpdateException("jobPosition", "id", id);
        }

        return jobPositionMapper.mapToDTO(jobPosition);
    }

    @Override
    public JobPositionResponseDTO delete(long id) {
        JobPosition jobPosition = findEntityById(id);

        checkOwnership(jobPosition);

        try {
            jobPositionRepository.delete(jobPosition);
        } catch (Exception e) {
            throw new EntityDeletionException("jobPosition", "id", id);
        }

        return jobPositionMapper.mapToDTO(jobPosition);
    }

    @Override
    public void checkOwnership(JobPosition jobPosition) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (jobPosition.getUser().getId() != user.getId())
            throw new AccessDeniedException("JobPosition access denied.");
    }
}