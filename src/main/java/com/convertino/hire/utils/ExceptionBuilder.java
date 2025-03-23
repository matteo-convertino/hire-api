package com.convertino.hire.utils;

import com.convertino.hire.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExceptionBuilder {
    /**
     * Collects validation errors from a {@link MethodArgumentNotValidException} and returns a map with the object name as key and the error message(s) as value.
     * <p>
     * It manages:
     * <li>single error messages, {objectName: errorMessage}</li>
     * <li>multiple error messages, {objectName: [errorMessage1, errorMessage2, ...]}</li>
     *
     * @param bindingResult the {@link BindingResult} containing validation errors
     * @return a {@link Map} where the keys are the field names or object names and the values are the corresponding error messages
     */
    public static Map<String, Object> collectErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .collect(Collectors.toMap(
                        ExceptionBuilder::getErrorKey,
                        ExceptionBuilder::getErrorMessage,
                        ExceptionBuilder::mergeErrors
                ));
    }

    /**
     * Retrieves the key for the given {@link ObjectError}.
     * <p>
     * If the error is a {@link FieldError}, the key will be the field name.
     * <p>
     * Otherwise, the key will be the object name.
     *
     * @param error the {@link ObjectError} to retrieve the key from
     * @return the key as a {@link String}, which is either the field name or the object name
     */
    private static String getErrorKey(ObjectError error) {
        if (error instanceof FieldError) {
            return ((FieldError) error).getField();
        } else {
            return error.getObjectName();
        }
    }

    /**
     * Retrieves the error message for the given {@link ObjectError}.
     * <p>
     * If the error has a default message, it will be returned.
     * <p>
     * Otherwise, "unknown error" will be returned.
     *
     * @param error the {@link ObjectError} to retrieve the error message from
     * @return the error message as a {@link String}
     */
    private static String getErrorMessage(ObjectError error) {
        String defaultMessage = error.getDefaultMessage();
        return defaultMessage != null ? defaultMessage : "unknown error";
    }

    /**
     * Merges two error messages into a single {@link List} of error messages.
     * <p>
     * If the existing error is a {@link String}, it will be converted to a {@link List} containing the existing error and the replacement error.
     * <p>
     * If the existing error is a {@link List}, the replacement error will be added to the existing list.
     *
     * @param existing    the existing error message
     * @param replacement the replacement error message
     * @return the merged error message
     */
    private static Object mergeErrors(Object existing, Object replacement) {
        if (existing instanceof String) {
            List<String> errorList = new ArrayList<>();
            errorList.add((String) existing);
            errorList.add((String) replacement);
            return errorList;
        } else if (existing instanceof List) {
            List<String> errorList = (List<String>) existing;
            errorList.add((String) replacement);
            return existing;
        }
        return replacement;
    }

    /**
     * Builds an {@link ErrorDTO} object with the given HTTP status, message, and request path.
     *
     * @param httpStatus the {@link HttpStatus} to set in the {@link ErrorDTO}
     * @param message    the error message or details to include in the {@link ErrorDTO}
     * @param path       the request URI path where the error occurred
     * @return an {@link ErrorDTO} containing the provided details
     */
    public static ErrorDTO buildErrorDTO(HttpStatus httpStatus, Object message, String path) {
        return new ErrorDTO(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message,
                path
        );
    }

    /**
     * Builds an {@link ErrorDTO} object with the given custom HTTP status, message, and request path.
     *
     * @param httpStatus the {@link CustomHttpStatus} to set in the {@link ErrorDTO}
     * @param message    the error message or details to include in the {@link ErrorDTO}
     * @param path       the request URI path where the error occurred
     * @return an {@link ErrorDTO} containing the provided details
     */
    public static ErrorDTO buildErrorDTO(CustomHttpStatus httpStatus, Object message, String path) {
        return new ErrorDTO(
                LocalDateTime.now(),
                httpStatus.getValue(),
                httpStatus.getReason(),
                message,
                path
        );
    }
}
