package com.x250.plants.notifications.scheduled_notifications_handling;

import com.x250.plants.notifications.scheduled_notifications_handling.email_sender.EmailSender;
import com.x250.plants.notifications.model.UsersPlantToWater;
import com.x250.plants.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailNotificationSender {

    private static final int MILLISECONDS_TO_SLEEP = 5000;

    private final NotificationRepository notificationRepository;

    private final EmailSender emailSender;

    public void send() {
        List<UsersPlantToWater> usersPlantsToWater =
                notificationRepository.findByEmailSentIsFalse();

        System.out.println("Database checked !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        usersPlantsToWater.forEach(n -> System.out.println(n + "\n"));

        for (UsersPlantToWater usersPlantToWater : usersPlantsToWater) {

            sendEmailNotification(usersPlantToWater);
            usersPlantToWater.setEmailSent(true);
            notificationRepository.save(usersPlantToWater);

            System.out.println("email sent !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            try {
                Thread.sleep(MILLISECONDS_TO_SLEEP);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void sendEmailNotification(UsersPlantToWater usersPlantToWater) {
        String from = "emailsender666666@gmail.com";
        String recipientEmail = usersPlantToWater.getAppUserEmail();
        String subject = "Water " + usersPlantToWater.getPlantName();
        String text = "Your plant " + usersPlantToWater.getPlantName() + " needs some water";
        emailSender.sendEmail(from, recipientEmail, subject, text);
    }

}
