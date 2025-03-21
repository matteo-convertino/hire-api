package com.convertino.hire.repository;

import com.convertino.hire.model.JobPosition;
import com.convertino.hire.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByJobPosition(JobPosition jobPosition);
}