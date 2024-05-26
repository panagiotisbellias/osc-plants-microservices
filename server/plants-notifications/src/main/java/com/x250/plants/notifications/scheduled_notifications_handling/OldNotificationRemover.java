package com.x250.plants.notifications.scheduled_notifications_handling;

import com.x250.plants.notifications.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OldNotificationRemover {

    @Value("${notificationservice.notificationsToRemoveAge}")
    private long notificationsToRemoveAge;

    private final NotificationRepository notificationRepository;

    @Transactional
    public void remove() {
        log.debug("remove()");
        notificationRepository
                .removeByEmailSentIsTrueOrNotificationDateBefore(LocalDateTime.now().minusHours(notificationsToRemoveAge));
        log.info("Removed outdated notification history");
    }

}