package com.x250.plants.authentication.exception_handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Component("delegatedAccessDeniedHandler")
public class DelegatedAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public DelegatedAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.debug("handle({}, {}, {})", request.getClass(), response.getClass(), accessDeniedException.toString());
        HashMap<Object, Object> responseBody = new HashMap<>();
        responseBody.put(HttpStatus.FORBIDDEN.toString(), accessDeniedException.getMessage());
        log.info("Response body: {}", responseBody);
        String jsonResponse = objectMapper.writeValueAsString(responseBody);
        log.info("JSON response: {}", jsonResponse);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(jsonResponse);
        log.info("Response got updated");
    }
}
