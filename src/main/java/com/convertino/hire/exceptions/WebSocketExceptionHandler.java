package com.convertino.hire.exceptions;

import com.convertino.hire.dto.ErrorDTO;
import com.convertino.hire.utils.routes.WebSocketRoutes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

import static com.convertino.hire.utils.ExceptionBuilder.buildErrorDTO;
import static com.convertino.hire.utils.ExceptionBuilder.collectErrors;

@ControllerAdvice
public class WebSocketExceptionHandler {

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendToUser(WebSocketRoutes.QUEUE_ERRORS)
    public ResponseEntity<ErrorDTO> handleValidationException(MethodArgumentNotValidException e, Message<?> message) {
        Map<String, Object> errorMap = null;

        if (e.getBindingResult() != null) {
            errorMap = collectErrors(e.getBindingResult());
        }

        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.BAD_REQUEST,
                errorMap,
                (String) message.getHeaders().get(SimpMessageHeaderAccessor.DESTINATION_HEADER)
        );

        return ResponseEntity.badRequest().body(errorDTO);
    }
}
