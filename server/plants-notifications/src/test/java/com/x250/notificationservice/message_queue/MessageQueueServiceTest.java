package com.x250.notificationservice.message_queue;

import com.x250.notificationservice.event.PlantWateringEvent;
import com.x250.notificationservice.model.UsersPlantToWater;
import com.x250.notificationservice.repository.NotificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageQueueServiceTest {

    @InjectMocks
    MessageQueueService messageQueueService;

    @Mock
    NotificationRepository notificationRepository;

    @Test
    void testConstructor() {
        MessageQueueService messageQueueService = new MessageQueueService(notificationRepository);
        Assertions.assertInstanceOf(MessageQueueService.class, messageQueueService);
    }

    @Test
    void testHandleNotification() {
        PlantWateringEvent plantWateringEvent = Mockito.mock(PlantWateringEvent.class);
        messageQueueService.handleNotification(plantWateringEvent);

        Mockito.verify(plantWateringEvent, Mockito.times(2)).usersPlantId();
        Mockito.verify(plantWateringEvent).plantName();
        Mockito.verify(plantWateringEvent).uppUserId();
        Mockito.verify(plantWateringEvent).uppUserEmail();
        Mockito.verify(plantWateringEvent).notificationDate();
        Mockito.verify(notificationRepository).save(ArgumentMatchers.any(UsersPlantToWater.class));
    }

}
