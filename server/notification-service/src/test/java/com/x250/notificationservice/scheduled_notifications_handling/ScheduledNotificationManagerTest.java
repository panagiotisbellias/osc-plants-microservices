package com.x250.notificationservice.scheduled_notifications_handling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScheduledNotificationManagerTest {

    @InjectMocks
    ScheduledNotificationManager scheduledNotificationManager;

    @Mock
    EmailNotificationSender emailNotificationSender;

    @Mock
    OldNotificationRemover oldNotificationRemover;

    @Test
    void testConstructor() {
        ScheduledNotificationManager scheduledNotificationManager = new ScheduledNotificationManager(emailNotificationSender, oldNotificationRemover);
        Assertions.assertInstanceOf(ScheduledNotificationManager.class, scheduledNotificationManager);
    }

    @Test
    void testSendByEmailAndRemoveOld() {
        scheduledNotificationManager.sendByEmailAndRemoveOld();
        Mockito.verify(emailNotificationSender).send();
        Mockito.verify(oldNotificationRemover).remove();
    }

}
