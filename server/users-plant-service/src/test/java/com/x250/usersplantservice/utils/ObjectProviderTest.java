package com.x250.usersplantservice.utils;

import com.x250.usersplantservice.exception.EntityNotFoundException;
import com.x250.usersplantservice.model.Plant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ObjectProviderTest {

    @Mock
    JpaRepository<Plant, String> repository;

    @Test
    void testGetObjectFromDB() throws EntityNotFoundException {
        Plant plant = Mockito.mock(Plant.class);
        Mockito.when(repository.findById("object id")).thenReturn(Optional.of(plant));
        Assertions.assertInstanceOf(Plant.class, ObjectProvider.getObjectFromDB("object id", repository));
    }

}
