package com.x250.usersplantservice.dto;

import com.x250.usersplantservice.model.Plant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class UsersPlantResponseDTOTest {

    @Test
    void testUsersPlantResponseDTO() {
        Plant plant = Mockito.mock(Plant.class);
        LocalDateTime nextWatering = Mockito.mock(LocalDateTime.class);

        UsersPlantResponseDTO usersPlantResponseDTO = new UsersPlantResponseDTO(0L, "app user id", plant, nextWatering);
        Assertions.assertEquals(0L, usersPlantResponseDTO.id());
        Assertions.assertEquals("app user id", usersPlantResponseDTO.appUserId());
        Assertions.assertInstanceOf(Plant.class, usersPlantResponseDTO.plant());
        Assertions.assertInstanceOf(LocalDateTime.class, usersPlantResponseDTO.nextWatering());
    }

}
