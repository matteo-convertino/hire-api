package com.convertino.hire.service;


import com.convertino.hire.dto.request.MessageRequestDTO;
import com.convertino.hire.dto.request.SkillRequestDTO;
import com.convertino.hire.dto.request.SkillUpdateRequestDTO;
import com.convertino.hire.dto.response.MessageResponseDTO;
import com.convertino.hire.dto.response.SkillResponseDTO;
import com.convertino.hire.model.Interview;
import com.convertino.hire.model.Message;
import com.convertino.hire.model.User;
import swiss.ameri.gemini.api.Content;

import java.util.List;

public interface MessageService {
    List<MessageResponseDTO> findAllByInterviewId(User user, long interviewId);
    List<Message> findAllEntityByInterviewId(User user, long interviewId);
    List<Message> findAllEntityByInterview(User user, Interview interview);
    MessageResponseDTO save(User user, MessageRequestDTO messageRequestDTO, long interviewId, Content.Role role);
    MessageResponseDTO save(User user, MessageRequestDTO messageRequestDTO, Interview interview, Content.Role role);
}