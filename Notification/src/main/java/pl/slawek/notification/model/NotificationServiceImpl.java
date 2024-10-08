package pl.slawek.notification.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pl.slawek.notification.dto.NotificationInfoDto;
import pl.slawek.notification.email.EmailSender;

@AllArgsConstructor
@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final EmailSender emailSender;

    @Override
    @RabbitListener(queues = "enroll_finish")
    public void handleFinishEnroll(NotificationInfoDto notificationInfo) {
        emailSender.sendEmails(notificationInfo);
    }
}