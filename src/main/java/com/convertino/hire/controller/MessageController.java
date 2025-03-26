package com.convertino.hire.controller;

import com.convertino.hire.dto.request.MessageRequestDTO;
import com.convertino.hire.dto.response.MessageResponseDTO;
import com.convertino.hire.exceptions.auth.InvalidCredentialsException;
import com.convertino.hire.model.User;
import com.convertino.hire.service.GeminiService;
import com.convertino.hire.service.MessageService;
import com.convertino.hire.utils.routes.MessageRoutes;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import swiss.ameri.gemini.api.Content;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class MessageController {

    private final GeminiService geminiService;
    private final MessageService messageService;

    @MessageMapping(MessageRoutes.SAVE)
    @SendToUser(MessageRoutes.SAVE_REPLIES)
    public ResponseEntity<MessageResponseDTO> save(@DestinationVariable long interviewId, @Valid MessageRequestDTO messageRequestDTO, Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getUser() == null) throw new InvalidCredentialsException();

        User user = (User) ((UsernamePasswordAuthenticationToken) accessor.getUser()).getPrincipal();

        messageService.save(user, messageRequestDTO, interviewId, Content.Role.USER);

        MessageRequestDTO response = geminiService.generateResponse(
                user,
                interviewId,
                messageService.findAllEntityByInterviewId(user, interviewId)
        );

        return ResponseEntity.ok(messageService.save(user, response, interviewId, Content.Role.MODEL));
    }
}
