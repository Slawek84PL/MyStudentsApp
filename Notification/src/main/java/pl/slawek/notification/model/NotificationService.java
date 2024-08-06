package pl.slawek.notification.model;

import pl.slawek.notification.dto.NotificationInfoDto;

public interface NotificationService {
    void handleFinishEnroll(NotificationInfoDto notificationInfoDto);
}