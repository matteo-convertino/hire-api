package com.convertino.hire.service.impl;


import com.convertino.hire.dto.request.SkillRequestDTO;
import com.convertino.hire.dto.request.SkillUpdateRequestDTO;
import com.convertino.hire.dto.response.SkillResponseDTO;
import com.convertino.hire.exceptions.entity.EntityCreationException;
import com.convertino.hire.exceptions.entity.EntityDeletionException;
import com.convertino.hire.exceptions.entity.EntityNotFoundException;
import com.convertino.hire.exceptions.entity.EntityUpdateException;
import com.convertino.hire.mapper.SkillMapper;
import com.convertino.hire.model.JobPosition;
import com.convertino.hire.model.Skill;
import com.convertino.hire.model.User;
import com.convertino.hire.repository.SkillRepository;
import com.convertino.hire.service.JobPositionService;
import com.convertino.hire.service.SkillService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillMapper skillMapper;
    private final SkillRepository skillRepository;
    private final JobPositionService jobPositionService;

    @Override
    public Skill findEntityById(long id) {
        return skillRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("skill", "id", id));
    }

    @Override
    public SkillResponseDTO findById(long id) {
        Skill skill = findEntityById(id);

        checkOwnership(skill);

        return skillMapper.mapToDTO(skill);
    }

    @Override
    public List<SkillResponseDTO> findAllByJobPositionId(long jobPositionId) {
        JobPosition jobPosition = jobPositionService.findEntityById(jobPositionId);

        jobPositionService.checkOwnership(jobPosition);

        return skillMapper.mapToDTO(skillRepository.findAllByJobPosition(jobPosition));
    }

    @Override
    public SkillResponseDTO save(SkillRequestDTO skillRequestDTO) {
        JobPosition jobPosition = jobPositionService.findEntityById(skillRequestDTO.getJobPositionId());
        jobPositionService.checkOwnership(jobPosition);

        Skill skill = skillMapper.mapToSkill(skillRequestDTO);
        skill.setJobPosition(jobPosition);

        try {
            skill = skillRepository.save(skill);
        } catch (Exception e) {
            throw new EntityCreationException("skill");
        }

        return skillMapper.mapToDTO(skill);
    }

    @Override
    public SkillResponseDTO update(long id, SkillUpdateRequestDTO skillUpdateRequestDTO) {
        Skill skill = findEntityById(id);

        checkOwnership(skill);

        skill.setDescription(skillUpdateRequestDTO.getDescription());

        try {
            skill = skillRepository.save(skill);
        } catch (Exception e) {
            throw new EntityUpdateException("skill", "id", id);
        }

        return skillMapper.mapToDTO(skill);
    }

    @Override
    public SkillResponseDTO delete(long id) {
        Skill skill = findEntityById(id);

        checkOwnership(skill);

        try {
            skillRepository.delete(skill);
        } catch (Exception e) {
            throw new EntityDeletionException("jobPosition", "id", id);
        }

        return skillMapper.mapToDTO(skill);
    }

    private void checkOwnership(Skill skill) {
        jobPositionService.checkOwnership(skill.getJobPosition());
    }

}