package io.santiagovogit.example.bounded_context.infrastructure.input_adapters.error;

import io.santiagovogit.example.shared.domain.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorDTO> handleDomainException(DomainException exc) {
        return handleException(exc, HttpStatus.BAD_REQUEST, "Domain exception occurred");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDTO> handleAccessDeniedException(AccessDeniedException exc) {
        return handleException(exc, HttpStatus.FORBIDDEN, "Access denied");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDTO> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException exc) {
        return handleException(exc, HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception exc) {
        return handleException(exc, HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected exception occurred");
    }

    private ResponseEntity<ErrorDTO> handleException(Exception exc, HttpStatus status, String logMessage) {
        final LocalDateTime timestamp = LocalDateTime.now();
        final int statusCode = status.value();
        final UUID errorId = UUID.randomUUID();
        final String error = exc.getMessage();
        final String details = getExceptionDetails(exc);

        MDC.put("errorId", errorId.toString());
        log.error("{} - {}", logMessage, errorId, exc);
        MDC.remove("errorId");

        final ErrorDTO response = ErrorDTO.builder()
                .timestamp(timestamp)
                .status(statusCode)
                .errorId(errorId)
                .error(error)
                .details(details)
                .build();

        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    private String getExceptionDetails(Exception exc) {
        if (exc instanceof DomainException domainException) {
            return domainException.getDetails();
        }
        return exc.getCause() != null ? exc.getCause().toString() : null;
    }
}

