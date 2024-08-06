package pl.slawek.notification.model;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    @RabbitListener(queues = "enroll_finish")
    public void handleFinishEnroll(String courseId) {
        System.out.println(courseId);
    }
}