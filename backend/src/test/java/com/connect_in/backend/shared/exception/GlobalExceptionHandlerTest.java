package com.connect_in.backend.shared.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @Test
    void handleForbiddenReturnsStatus403() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        var response = handler.handleForbidden(new ForbiddenException("Access denied."));

        Map<String, Object> body = response.getBody();
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(403, body.get("status"));
    }
}
