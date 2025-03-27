package com.convertino.hire.exceptions;

import com.convertino.hire.dto.ErrorDTO;
import com.convertino.hire.exceptions.entity.EntityCreationException;
import com.convertino.hire.exceptions.entity.EntityNotFoundException;
import com.convertino.hire.exceptions.websocket.GeminiException;
import com.convertino.hire.exceptions.websocket.MessageNotAllowedException;
import com.convertino.hire.utils.routes.WebSocketRoutes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

import static com.convertino.hire.utils.ExceptionBuilder.buildErrorDTO;
import static com.convertino.hire.utils.ExceptionBuilder.collectErrors;

@ControllerAdvice
public class WebSocketExceptionHandler {

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendToUser(WebSocketRoutes.QUEUE_ERRORS)
    public ResponseEntity<ErrorDTO> handleValidationException(
            MethodArgumentNotValidException exception,
            Message<?> message
    ) {
        Map<String, Object> errorMap = null;

        if (exception.getBindingResult() != null) {
            errorMap = collectErrors(exception.getBindingResult());
        }

        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.BAD_REQUEST,
                errorMap,
                (String) message.getHeaders().get(SimpMessageHeaderAccessor.DESTINATION_HEADER)
        );

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @MessageExceptionHandler(MessageNotAllowedException.class)
    @SendToUser(WebSocketRoutes.QUEUE_ERRORS)
    public ResponseEntity<ErrorDTO> handleBadRequestException(
            RuntimeException exception,
            Message<?> message
    ) {
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                (String) message.getHeaders().get(SimpMessageHeaderAccessor.DESTINATION_HEADER)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }


    @MessageExceptionHandler({
            EntityCreationException.class,
            GeminiException.class
    })
    @SendToUser(WebSocketRoutes.QUEUE_ERRORS)
    public ResponseEntity<ErrorDTO> handleBadGatewayException(
            RuntimeException exception,
            Message<?> message
    ) {
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.BAD_GATEWAY,
                exception.getMessage(),
                (String) message.getHeaders().get(SimpMessageHeaderAccessor.DESTINATION_HEADER)
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorDTO);
    }

    @MessageExceptionHandler(AccessDeniedException.class)
    @SendToUser(WebSocketRoutes.QUEUE_ERRORS)
    public ResponseEntity<ErrorDTO> handleForbiddenException(
            RuntimeException exception,
            Message<?> message
    ) {
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.FORBIDDEN,
                exception.getMessage(),
                (String) message.getHeaders().get(SimpMessageHeaderAccessor.DESTINATION_HEADER)
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDTO);
    }

    @MessageExceptionHandler(EntityNotFoundException.class)
    @SendToUser(WebSocketRoutes.QUEUE_ERRORS)
    public ResponseEntity<ErrorDTO> handleNotFoundException(
            RuntimeException exception,
            Message<?> message
    ) {
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                (String) message.getHeaders().get(SimpMessageHeaderAccessor.DESTINATION_HEADER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }
}
