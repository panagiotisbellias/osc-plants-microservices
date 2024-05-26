package com.x250.plants.notifications.scheduled_notifications_handling;

import com.x250.plants.notifications.scheduled_notifications_handling.email_sender.EmailSender;
import com.x250.plants.notifications.model.UsersPlantToWater;
import com.x250.plants.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationSender {

    private static final int MILLISECONDS_TO_SLEEP = 5000;

    private final NotificationRepository notificationRepository;

    private final EmailSender emailSender;

    public void send() {
        log.debug("send()");
        List<UsersPlantToWater> usersPlantsToWater =
                notificationRepository.findByEmailSentIsFalse();

        log.info("Database checked ! Found plants to be watered");
        usersPlantsToWater.forEach(n -> log.info(n.toString()));

        for (UsersPlantToWater usersPlantToWater : usersPlantsToWater) {

            sendEmailNotification(usersPlantToWater);
            usersPlantToWater.setEmailSent(true);
            notificationRepository.save(usersPlantToWater);

            log.info("email sent ! Notification saved");
            try {
                Thread.sleep(MILLISECONDS_TO_SLEEP);
                log.info("Waited {}", MILLISECONDS_TO_SLEEP);
            } catch (InterruptedException ie) {
                log.error(Arrays.toString(ie.getStackTrace()));
                Thread.currentThread().interrupt();
            }
        }
    }

    private void sendEmailNotification(UsersPlantToWater usersPlantToWater) {
        log.debug("sendEmailNotification({})", usersPlantToWater.toString());
        String from = "emailsender666666@gmail.com";
        String recipientEmail = usersPlantToWater.getAppUserEmail();
        String subject = "Water " + usersPlantToWater.getPlantName();
        String text = "Your plant " + usersPlantToWater.getPlantName() + " needs some water";
        log.info("Email to be sent: {}, {}, {}, {}", from, recipientEmail, subject, text);
        emailSender.sendEmail(from, recipientEmail, subject, text);
    }

}
