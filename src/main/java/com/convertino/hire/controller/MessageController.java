package com.convertino.hire.controller;

import com.convertino.hire.dto.request.MessageRequestDTO;
import com.convertino.hire.utils.routes.MessageRoutes;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MessageController {
    @MessageMapping(MessageRoutes.SAVE)
    @SendToUser(MessageRoutes.SAVE_REPLIES)
    public ResponseEntity<String> save(@DestinationVariable long interviewId, @Valid MessageRequestDTO messageRequestDTO) {
        System.out.println(interviewId);
        System.out.println(messageRequestDTO);
        return ResponseEntity.ok("OK");
    }
}
