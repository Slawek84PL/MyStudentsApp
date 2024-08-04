package pl.slawek.course.notification;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationService {

    private final RabbitTemplate rabbitTemplate;

    public void sendNotification(String courseId) {
        rabbitTemplate.convertAndSend("kurs", courseId);
    }

}
