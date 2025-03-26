package com.convertino.hire.repository;

import com.convertino.hire.model.Interview;
import com.convertino.hire.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByInterview(Interview interview);
}