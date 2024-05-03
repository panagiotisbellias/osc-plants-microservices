package com.x250.plants.authentication.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class DemoControllerTest {

    @InjectMocks
    DemoController demoController;

    @Test
    void testSayHello() {
        Assertions.assertEquals(ResponseEntity.ok("Hello from secured endpoint"), demoController.sayHello());
    }

}
