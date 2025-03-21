package com.convertino.hire.repository;

import com.convertino.hire.model.JobPosition;
import com.convertino.hire.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
    List<JobPosition> findAllByUser(User user);
}