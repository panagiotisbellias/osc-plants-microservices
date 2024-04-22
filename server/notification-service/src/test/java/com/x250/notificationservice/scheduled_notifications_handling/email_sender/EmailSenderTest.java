package com.x250.notificationservice.scheduled_notifications_handling.email_sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailSenderTest {

    @InjectMocks
    EmailSender emailSender;

    @Mock
    JavaMailSender javaMailSender;

    @Test
    void testConstructor() {
        EmailSender emailSender = new EmailSender(javaMailSender);
        Assertions.assertInstanceOf(EmailSender.class, emailSender);
    }

    @Test
    void testSendEmail() {
        emailSender.sendEmail("from", "to", "subject", "text");
        Mockito.verify(javaMailSender).send(ArgumentMatchers.any(SimpleMailMessage.class));
    }

}
