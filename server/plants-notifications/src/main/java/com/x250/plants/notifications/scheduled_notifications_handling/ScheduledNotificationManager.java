package com.x250.plants.notifications.scheduled_notifications_handling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledNotificationManager {

    private final EmailNotificationSender emailNotificationSender;
    private final OldNotificationRemover oldNotificationRemover;

    @Scheduled(fixedDelay = 60000) // every hour
    public void sendByEmailAndRemoveOld() {
        log.debug("sendByEmailAndRemoveOld()");
        emailNotificationSender.send();
        oldNotificationRemover.remove();
        log.info("Sent the scheduled email and removed old notifications");
    }

}
