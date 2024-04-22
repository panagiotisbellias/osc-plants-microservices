package com.x250.notificationservice.scheduled_notifications_handling;

import com.x250.notificationservice.repository.NotificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class OldNotificationRemoverTest {

    @InjectMocks
    OldNotificationRemover oldNotificationRemover;

    @Mock
    NotificationRepository notificationRepository;

    @Test
    void testConstructor() {
        OldNotificationRemover oldNotificationRemover = new OldNotificationRemover(notificationRepository);
        Assertions.assertInstanceOf(OldNotificationRemover.class, oldNotificationRemover);
    }

    @Test
    void testRemove() {
        oldNotificationRemover.remove();
        Mockito.verify(notificationRepository).removeByEmailSentIsTrueOrNotificationDateBefore(ArgumentMatchers.any(LocalDateTime.class));
    }

}
