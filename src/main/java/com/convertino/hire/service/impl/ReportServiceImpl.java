package com.convertino.hire.service.impl;


import com.convertino.hire.dto.request.ReportRequestDTO;
import com.convertino.hire.dto.request.SkillRequestDTO;
import com.convertino.hire.dto.request.SkillUpdateRequestDTO;
import com.convertino.hire.dto.response.ReportResponseDTO;
import com.convertino.hire.dto.response.SkillResponseDTO;
import com.convertino.hire.exceptions.entity.EntityCreationException;
import com.convertino.hire.exceptions.entity.EntityDeletionException;
import com.convertino.hire.exceptions.entity.EntityNotFoundException;
import com.convertino.hire.exceptions.entity.EntityUpdateException;
import com.convertino.hire.mapper.ReportMapper;
import com.convertino.hire.mapper.SkillMapper;
import com.convertino.hire.model.*;
import com.convertino.hire.repository.ReportRepository;
import com.convertino.hire.repository.SkillRepository;
import com.convertino.hire.service.InterviewService;
import com.convertino.hire.service.JobPositionService;
import com.convertino.hire.service.ReportService;
import com.convertino.hire.service.SkillService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportMapper reportMapper;
    private final ReportRepository reportRepository;
    private final InterviewService interviewService;

    @Override
    public ReportResponseDTO save(User user, ReportRequestDTO reportRequestDTO) {
        Interview interview = interviewService.findEntityById(reportRequestDTO.getInterviewId());
        checkOwnership(interview, user);

        Optional<Skill> skillOptional = interview.getJobPosition().getSkills()
                .stream()
                .filter(s -> s.getId() == reportRequestDTO.getSkillId())
                .findFirst();

        if(skillOptional.isEmpty()) throw new EntityNotFoundException("skill", "id", reportRequestDTO.getSkillId());

        Report report = reportMapper.mapToReport(reportRequestDTO);
        report.setInterview(interview);
        report.setSkill(skillOptional.get());

        try {
            report = reportRepository.save(report);
        } catch (Exception e) {
            throw new EntityCreationException("report");
        }

        return reportMapper.mapToDTO(report);
    }

    private void checkOwnership(Interview interview, User user) {
        if (interview.getUser().getId() == user.getId() || interview.getJobPosition().getUser().getId() == user.getId())
            return;

        throw new AccessDeniedException("Interview access denied.");
    }
}