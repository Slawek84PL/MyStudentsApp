package pl.slawek.notification;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    @RabbitListener(queues = "StudentNotification")
    public void getNotifications(String courseId) {
        System.out.println(courseId);
    }
}