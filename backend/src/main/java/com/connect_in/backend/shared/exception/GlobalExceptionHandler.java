package com.connect_in.backend.shared.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 401 — Bad credentials (login)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Bad credentials: {}", ex.getMessage());
        return errorResponse(HttpStatus.UNAUTHORIZED, "Email or password is incorrect.");
    }

    // 401 — Authentication failed
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthentication(AuthenticationException ex) {
        log.warn("Not authenticated: {}", ex.getMessage());
        return errorResponse(HttpStatus.UNAUTHORIZED, "Not authenticated.");
    }

    // 404 — Resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 403 — Access denied (author != current user)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbidden(ForbiddenException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return errorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    // 403 — Access denied (Spring Security)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return errorResponse(HttpStatus.FORBIDDEN, "Access denied.");
    }

    // 409 — Conflict (duplicate membership, etc.)
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(ConflictException ex) {
        log.warn("Conflict: {}", ex.getMessage());
        return errorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // 413 — File too large
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, Object>> handleMaxUploadSize(MaxUploadSizeExceededException ex) {
        log.warn("File too large: {}", ex.getMessage());
        return errorResponse(HttpStatus.PAYLOAD_TOO_LARGE, "File too large (max 10MB).");
    }

    // 422 — Domain validation error (email taken, wrong password, etc.)
    // Frontend format: { "errors": { "field": ["message"] } }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(ValidationException ex) {
        log.warn("Validation error: {}", ex.getMessage());
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("message", List.of(ex.getMessage()));
        return validationResponse(HttpStatus.UNPROCESSABLE_ENTITY, errors);
    }

    // 422 — Jakarta Validation errors (@NotBlank, @Email, @Size, etc.)
    // Frontend format: { "errors": { "field": ["message"] } }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        });
        return validationResponse(HttpStatus.UNPROCESSABLE_ENTITY, errors);
    }

    // 500 — Unexpected error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Unexpected error", ex);
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An internal error occurred.");
    }

    private ResponseEntity<Map<String, Object>> errorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("message", message);
        body.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(status).body(body);
    }

    private ResponseEntity<Map<String, Object>> validationResponse(HttpStatus status, Map<String, List<String>> errors) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("errors", errors);
        body.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(status).body(body);
    }
}
