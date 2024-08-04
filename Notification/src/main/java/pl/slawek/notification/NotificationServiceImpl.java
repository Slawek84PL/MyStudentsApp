package pl.slawek.notification;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    @RabbitListener
    public void getNotifications() {
        rabbitTemplate.setExchange("Student");
        rabbitTemplate.setRoutingKey("StudentNotification");
        Message receive = rabbitTemplate.receive();
        System.out.println(receive);
    }
}