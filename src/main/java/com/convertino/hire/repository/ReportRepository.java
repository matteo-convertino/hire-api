package com.convertino.hire.repository;

import com.convertino.hire.model.Report;
import com.convertino.hire.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByInterview_Id(long interviewId);
    List<Report> findAllByInterview_JobPosition_User(User user);
}