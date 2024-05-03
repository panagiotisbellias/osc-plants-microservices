package com.x250.notificationservice.scheduled_notifications_handling;

import com.x250.notificationservice.model.UsersPlantToWater;
import com.x250.notificationservice.repository.NotificationRepository;
import com.x250.notificationservice.scheduled_notifications_handling.email_sender.EmailSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class EmailNotificationSenderTest {

    @InjectMocks
    EmailNotificationSender emailNotificationSender;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    EmailSender emailSender;

    @Test
    void testConstructor() {
        EmailNotificationSender emailNotificationSender = new EmailNotificationSender(notificationRepository, emailSender);
        Assertions.assertInstanceOf(EmailNotificationSender.class, emailNotificationSender);
    }

    @Test
    void testSend() {
        UsersPlantToWater usersPlantToWater = Mockito.mock(UsersPlantToWater.class);
        Mockito.when(notificationRepository.findByEmailSentIsFalse()).thenReturn(List.of(usersPlantToWater));
        emailNotificationSender.send();

        Mockito.verify(notificationRepository).findByEmailSentIsFalse();
        Mockito.verify(usersPlantToWater).getAppUserEmail();
        Mockito.verify(usersPlantToWater, Mockito.times(2)).getPlantName();
        Mockito.verify(emailSender).sendEmail("emailsender666666@gmail.com", null, "Water " + null, "Your plant " + null + " needs some water");
        Mockito.verify(usersPlantToWater).setEmailSent(true);
        Mockito.verify(notificationRepository).save(usersPlantToWater);
    }

}
