package com.x250.authenticationservice.dto;

import com.x250.authenticationservice.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegisterRequestTest {

    @Test
    void testRegisterRequest() {
        Role role = Mockito.mock(Role.class);
        RegisterRequest registerRequest = new RegisterRequest("username", "test@email.com", "password", role);

        Assertions.assertEquals("username", registerRequest.username());
        Assertions.assertEquals("test@email.com", registerRequest.email());
        Assertions.assertEquals("password", registerRequest.password());
        Assertions.assertInstanceOf(Role.class, registerRequest.role());
    }

}
