package com.convertino.hire.repository;

import com.convertino.hire.model.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {

}