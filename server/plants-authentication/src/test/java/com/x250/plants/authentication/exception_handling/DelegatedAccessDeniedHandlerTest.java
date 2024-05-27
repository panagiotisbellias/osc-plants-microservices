package com.x250.plants.authentication.exception_handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
class DelegatedAccessDeniedHandlerTest {

    @InjectMocks
    DelegatedAccessDeniedHandler delegatedAccessDeniedHandler;

    @Mock
    ObjectMapper objectMapper;

    @Test
    void testConstructor() {
        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
        DelegatedAccessDeniedHandler delegatedAccessDeniedHandler = new DelegatedAccessDeniedHandler(objectMapper);
        Assertions.assertInstanceOf(DelegatedAccessDeniedHandler.class, delegatedAccessDeniedHandler);
    }

    @Test
    void testHandle() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        AccessDeniedException accessDeniedException = Mockito.mock(AccessDeniedException.class);
        PrintWriter writer = Mockito.mock(PrintWriter.class);

        Mockito.when(objectMapper.writeValueAsString(ArgumentMatchers.any(HashMap.class))).thenReturn("{\"testKey\":\"testValue\"}");
        Mockito.when(response.getWriter()).thenReturn(writer);
        delegatedAccessDeniedHandler.handle(request, response, accessDeniedException);

        Mockito.verify(accessDeniedException).getMessage();
        Mockito.verify(objectMapper).writeValueAsString(ArgumentMatchers.any(HashMap.class));
        Mockito.verify(response).setContentType("application/json");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        Mockito.verify(response).getWriter();
        Mockito.verify(writer).write("{\"testKey\":\"testValue\"}");
    }

}
