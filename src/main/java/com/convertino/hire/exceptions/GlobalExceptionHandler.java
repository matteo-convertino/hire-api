package com.convertino.hire.exceptions;

import com.convertino.hire.dto.ErrorDTO;
import com.convertino.hire.exceptions.auth.InvalidCredentialsException;
import com.convertino.hire.exceptions.entity.*;
import com.convertino.hire.exceptions.user.InvalidPasswordException;
import com.convertino.hire.exceptions.websocket.GeminiException;
import com.convertino.hire.utils.CustomHttpStatus;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static com.convertino.hire.utils.ExceptionBuilder.buildErrorDTO;
import static com.convertino.hire.utils.ExceptionBuilder.collectErrors;

/**
 * Global exception handler for the application.
 * <p>
 * This class handles various exceptions and maps them to appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link MethodArgumentNotValidException} and returns a {@link ResponseEntity} with a BAD_REQUEST status.
     *
     * @param exception  the {@link MethodArgumentNotValidException} thrown
     * @param webRequest the current web request
     * @return a {@link ResponseEntity} containing an {@link ErrorDTO} with details of the validation error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(
            MethodArgumentNotValidException exception,
            WebRequest webRequest
    ) {
        Map<String, Object> errorMap = collectErrors(exception.getBindingResult());
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.BAD_REQUEST,
                errorMap,
                ((ServletWebRequest) webRequest).getRequest().getRequestURI()
        );
        return ResponseEntity.badRequest().body(errorDTO);
    }

    /**
     * Handles {@link ConstraintViolationException} and returns a {@link ResponseEntity} with a BAD_REQUEST status.
     *
     * @param exception  the {@link ConstraintViolationException} thrown
     * @param webRequest the current web request
     * @return a {@link ResponseEntity} containing an {@link ErrorDTO} with details of the constraint violation
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(
            ConstraintViolationException exception,
            WebRequest webRequest
    ) {
        String message = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Constraint violation");
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.BAD_REQUEST,
                message,
                ((ServletWebRequest) webRequest).getRequest().getRequestURI()
        );
        return ResponseEntity.badRequest().body(errorDTO);
    }

    /**
     * Handles {@link InvalidFormatException} and returns a {@link ResponseEntity} with a BAD_REQUEST status.
     * <p>
     * {@link InvalidFormatException} is thrown by Jackson when it cannot deserialize a value to a specific type (e.g. @JsonFormat is violated).
     *
     * @param exception  the {@link InvalidFormatException} thrown
     * @param webRequest the current web request
     * @return a {@link ResponseEntity} containing an {@link ErrorDTO} with details of the invalid format
     */
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorDTO> handleInvalidFormatException(
            InvalidFormatException exception,
            WebRequest webRequest
    ) {
        String message = String.format(
                "Invalid value '%s' for %s field.",
                exception.getValue(),
                exception.getPath().getFirst().getFieldName()
        );
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.BAD_REQUEST,
                message,
                ((ServletWebRequest) webRequest).getRequest().getRequestURI()
        );
        return ResponseEntity.badRequest().body(errorDTO);
    }

    /**
     * Handles various exceptions related to bad requests and returns a {@link ResponseEntity} with a BAD_REQUEST status.
     *
     * @param exception  the {@link RuntimeException} thrown
     * @param webRequest the current web request
     * @return a {@link ResponseEntity} containing an {@link ErrorDTO} with details of the bad request error
     */
    /*ExceptionHandler({
            InvalidWeekDayException.class,
            SittingTimeOverlappingException.class,
            BookingNotAllowedException.class,
            InvalidBookingWeekDayException.class,
            InvalidBookingRestaurantException.class,
            InvalidBookingStateException.class,
            InvalidBookingSittingTimeException.class,
            OrderNotAllowedException.class,
            InvalidOrderStateException.class,
            ReviewNotAllowedException.class
    })
    public ResponseEntity<ErrorDTO> handleBadRequestException(
            RuntimeException exception,
            WebRequest webRequest
    ) {
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                ((ServletWebRequest) webRequest).getRequest().getRequestURI()
        );
        return ResponseEntity.badRequest().body(errorDTO);
    }*/

    /**
     * Handles unauthorized exceptions and returns a {@link ResponseEntity} with an UNAUTHORIZED status.
     *
     * @param exception  the {@link RuntimeException} thrown
     * @param webRequest the current web request
     * @return a {@link ResponseEntity} containing an {@link ErrorDTO} with details of the unauthorized error
     */
    @ExceptionHandler({
            InvalidCredentialsException.class,
            InvalidPasswordException.class
    })
    public ResponseEntity<ErrorDTO> handleUnauthorizedException(
            RuntimeException exception,
            WebRequest webRequest
    ) {
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.UNAUTHORIZED,
                exception.getMessage(),
                ((ServletWebRequest) webRequest).getRequest().getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    /**
     * Handles forbidden exceptions and returns a {@link ResponseEntity} with a FORBIDDEN status.
     *
     * @param exception  the {@link RuntimeException} thrown
     * @param webRequest the current web request
     * @return a {@link ResponseEntity} containing an {@link ErrorDTO} with details of the forbidden error
     */
    @ExceptionHandler({
            AccessDeniedException.class,
            AuthenticationException.class,
            /*UserNotActiveException.class,
            ForbiddenBookingAccessException.class,
            ForbiddenRestaurantAccessException.class,
            ForbiddenOrderAccessException.class,
            ForbiddenReviewAccessException.class*/
    })
    public ResponseEntity<ErrorDTO> handleForbiddenException(
            RuntimeException exception,
            WebRequest webRequest
    ) {
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.FORBIDDEN,
                exception.getMessage(),
                ((ServletWebRequest) webRequest).getRequest().getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDTO);
    }

    /**
     * Handles not found exceptions and returns a {@link ResponseEntity} with a NOT_FOUND status.
     *
     * @param exception  the {@link RuntimeException} thrown
     * @param webRequest the current web request
     * @return a {@link ResponseEntity} containing an {@link ErrorDTO} with details of the not found error
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(
            RuntimeException exception,
            WebRequest webRequest
    ) {
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                ((ServletWebRequest) webRequest).getRequest().getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    /**
     * Handles conflict exceptions and returns a {@link ResponseEntity} with a CONFLICT status.
     *
     * @param exception  the {@link RuntimeException} thrown
     * @param webRequest the current web request
     * @return a {@link ResponseEntity} containing an {@link ErrorDTO} with details of the conflict error
     */
    @ExceptionHandler({
            EntityDuplicateException.class,
            /*RestaurateurAlreadyHasRestaurantException.class,
            DuplicateActiveFutureBookingException.class*/
    })
    public ResponseEntity<ErrorDTO> handleConflictException(
            RuntimeException exception,
            WebRequest webRequest
    ) {
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                ((ServletWebRequest) webRequest).getRequest().getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDTO);
    }

    /**
     * Handles internal server error exceptions and returns a {@link ResponseEntity} with an INTERNAL_SERVER_ERROR status.
     *
     * @param exception  the {@link RuntimeException} thrown
     * @param webRequest the current web request
     * @return a {@link ResponseEntity} containing an {@link ErrorDTO} with details of the internal server error
     */
    @ExceptionHandler({
            MalformedJwtException.class,
            ExpiredJwtException.class,
            UnsupportedJwtException.class,
            SignatureException.class
    })
    public ResponseEntity<ErrorDTO> handleInvalidTokenException(
            RuntimeException exception,
            WebRequest webRequest
    ) {
        ErrorDTO errorDTO = buildErrorDTO(
                CustomHttpStatus.INVALID_TOKEN,
                exception.getMessage(),
                ((ServletWebRequest) webRequest).getRequest().getRequestURI()
        );
        return ResponseEntity.status(CustomHttpStatus.INVALID_TOKEN.getValue()).body(errorDTO);
    }

    /**
     * Handles bad gateway exceptions and returns a {@link ResponseEntity} with a BAD_GATEWAY status.
     *
     * @param exception  the {@link RuntimeException} thrown
     * @param webRequest the current web request
     * @return a {@link ResponseEntity} containing an {@link ErrorDTO} with details of the bad gateway error
     */
    @ExceptionHandler({
            EntityCreationException.class,
            EntityUpdateException.class,
            EntityDeletionException.class,
            /*GoogleDriveFileUploadException.class,
            GoogleDriveFileDeleteException.class,
            EmailSendingException.class,
            FirebaseCustomTokenCreationException.class*/
    })
    public ResponseEntity<ErrorDTO> handleBadGatewayException(
            RuntimeException exception,
            WebRequest webRequest
    ) {
        ErrorDTO errorDTO = buildErrorDTO(
                HttpStatus.BAD_GATEWAY,
                exception.getMessage(),
                ((ServletWebRequest) webRequest).getRequest().getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorDTO);
    }
}