package com.convertino.hire.repository;

import com.convertino.hire.model.Interview;
import com.convertino.hire.model.JobPosition;
import com.convertino.hire.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findAllByJobPosition_User(User user);
    List<Interview> findAllByJobPosition(JobPosition jobPosition);
}