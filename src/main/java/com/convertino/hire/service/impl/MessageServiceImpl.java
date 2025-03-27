package com.convertino.hire.service.impl;


import com.convertino.hire.dto.request.MessageRequestDTO;
import com.convertino.hire.dto.response.MessageResponseDTO;
import com.convertino.hire.exceptions.entity.EntityCreationException;
import com.convertino.hire.exceptions.websocket.MessageNotAllowedException;
import com.convertino.hire.mapper.MessageMapper;
import com.convertino.hire.model.Interview;
import com.convertino.hire.model.Message;
import com.convertino.hire.model.User;
import com.convertino.hire.repository.MessageRepository;
import com.convertino.hire.service.GeminiService;
import com.convertino.hire.service.InterviewService;
import com.convertino.hire.service.MessageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import swiss.ameri.gemini.api.Content;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;
    private final InterviewService interviewService;
    private final GeminiService geminiService;

    @Override
    public List<MessageResponseDTO> findAllByInterviewId(User user, long interviewId) {
        return messageMapper.mapToDTO(findAllEntityByInterviewId(user, interviewId));
    }

    @Override
    public List<Message> findAllEntityByInterviewId(User user, long interviewId) {
        Interview interview = interviewService.findEntityById(interviewId);

        return findAllEntityByInterview(user, interview);
    }

    @Override
    public List<Message> findAllEntityByInterview(User user, Interview interview) {
        checkOwnership(interview, user);

        return messageRepository.findAllByInterview(interview);
    }

    @Override
    public MessageResponseDTO save(User user, MessageRequestDTO messageRequestDTO, long interviewId, Content.Role role) {
        Interview interview = interviewService.findEntityById(interviewId);

        return save(user, messageRequestDTO, interview, role);
    }

    @Override
    public MessageResponseDTO save(User user, MessageRequestDTO messageRequestDTO, Interview interview, Content.Role role) {
        if (role == Content.Role.USER && interview.getCompletedAt() != null) throw new MessageNotAllowedException();
        if (role == Content.Role.USER) checkOwnership(interview, user);

        Message message = messageMapper.mapToMessage(messageRequestDTO);
        message.setRole(role);
        message.setInterview(interview);

        try {
            message = messageRepository.save(message);
        } catch (Exception e) {
            throw new EntityCreationException("message");
        }

        return messageMapper.mapToDTO(message);
    }

    @Override
    public MessageResponseDTO getGeminiResponse(User user, MessageRequestDTO messageRequestDTO, long interviewId) {
        Interview interview = interviewService.findEntityById(interviewId);

        save(user, messageRequestDTO, interview, Content.Role.USER);

        MessageRequestDTO geminiResponse = geminiService.generateResponse(
                user,
                interview,
                findAllEntityByInterview(user, interview)
        );

        if (geminiResponse.isLastMessage())
            interview = interviewService.setAsCompleted(interview);

        return save(user, geminiResponse, interview, Content.Role.MODEL);
    }

    private void checkOwnership(Interview interview, User user) {
        if (interview.getUser().getId() == user.getId() || interview.getJobPosition().getUser().getId() == user.getId())
            return;

        throw new AccessDeniedException("Interview access denied.");
    }
}