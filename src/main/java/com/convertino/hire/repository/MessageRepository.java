package com.convertino.hire.repository;

import com.convertino.hire.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, Long> {

}