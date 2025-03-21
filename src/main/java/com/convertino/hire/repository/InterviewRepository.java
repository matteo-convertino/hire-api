package com.convertino.hire.repository;

import com.convertino.hire.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InterviewRepository extends JpaRepository<Interview, Long> {

}