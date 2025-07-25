package com.convertino.hire.controller;

import com.convertino.hire.dto.request.MessageRequestDTO;
import com.convertino.hire.dto.response.MessageResponseDTO;
import com.convertino.hire.exceptions.auth.InvalidCredentialsException;
import com.convertino.hire.exceptions.entity.EntityNotFoundException;
import com.convertino.hire.model.User;
import com.convertino.hire.service.MessageService;
import com.convertino.hire.utils.routes.MessageRoutes;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping(MessageRoutes.SAVE)
    @SendToUser(MessageRoutes.SAVE_REPLIES)
    public ResponseEntity<MessageResponseDTO> save(@DestinationVariable long interviewId, @Valid MessageRequestDTO messageRequestDTO, Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getUser() == null) throw new InvalidCredentialsException();

        User user = (User) ((UsernamePasswordAuthenticationToken) accessor.getUser()).getPrincipal();

        return ResponseEntity.ok(messageService.getGeminiResponse(user, messageRequestDTO, interviewId));
    }

    @GetMapping(MessageRoutes.FIND_ALL_BY_INTERVIEW_ID)
    public ResponseEntity<List<MessageResponseDTO>> findByAllByInterviewId(@PathVariable long interviewId) throws EntityNotFoundException {
        return ResponseEntity.ok(messageService.findAllByInterviewId(interviewId));
    }
}
