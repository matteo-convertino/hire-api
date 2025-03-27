package com.convertino.hire.service.impl;


import com.convertino.hire.dto.request.InterviewRequestDTO;
import com.convertino.hire.dto.response.InterviewResponseDTO;
import com.convertino.hire.exceptions.entity.EntityCreationException;
import com.convertino.hire.exceptions.entity.EntityNotFoundException;
import com.convertino.hire.exceptions.entity.EntityUpdateException;
import com.convertino.hire.mapper.InterviewMapper;
import com.convertino.hire.model.Interview;
import com.convertino.hire.model.JobPosition;
import com.convertino.hire.model.User;
import com.convertino.hire.repository.InterviewRepository;
import com.convertino.hire.service.InterviewService;
import com.convertino.hire.service.JobPositionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class InterviewServiceImpl implements InterviewService {
    private final InterviewMapper interviewMapper;
    private final InterviewRepository interviewRepository;
    private final JobPositionService jobPositionService;

    @Override
    public Interview findEntityById(long id) {
        return interviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("interview", "id", id));
    }

    @Override
    public InterviewResponseDTO findById(long id) {
        Interview interview = findEntityById(id);

        checkOwnership(interview);

        return interviewMapper.mapToDTO(interview);
    }

    @Override
    public List<InterviewResponseDTO> findAllByUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return interviewMapper.mapToDTO(interviewRepository.findAllByJobPosition_User(user));
    }

    @Override
    public List<InterviewResponseDTO> findAllByJobPositionId(long jobPositionId) {
        JobPosition jobPosition = jobPositionService.findEntityById(jobPositionId);

        jobPositionService.checkOwnership(jobPosition);

        return interviewMapper.mapToDTO(interviewRepository.findAllByJobPosition(jobPosition));
    }

    @Override
    public InterviewResponseDTO save(InterviewRequestDTO interviewRequestDTO) {
        JobPosition jobPosition = jobPositionService.findEntityById(interviewRequestDTO.getJobPositionId());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Interview interview = interviewMapper.mapToInterview(interviewRequestDTO);

        interview.setJobPosition(jobPosition);
        interview.setUser(user);

        try {
            interview = interviewRepository.save(interview);
        } catch (Exception e) {
            throw new EntityCreationException("interview");
        }

        return interviewMapper.mapToDTO(interview);
    }

    @Override
    public void checkOwnership(Interview interview) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (interview.getUser().getId() != user.getId())
            throw new AccessDeniedException("Interview access denied.");

        jobPositionService.checkOwnership(interview.getJobPosition());
    }

    @Override
    public Interview setAsCompleted(Interview interview) {
        interview.setCompletedAt(LocalDateTime.now());

        try {
            interview = interviewRepository.save(interview);
        } catch (Exception e) {
            throw new EntityUpdateException("interview");
        }

        return interview;
    }
}