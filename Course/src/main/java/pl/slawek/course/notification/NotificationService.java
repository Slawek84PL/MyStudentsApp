package pl.slawek.course.notification;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pl.slawek.course.dto.NotificationInfoDto;
import pl.slawek.course.dto.StudentMember;
import pl.slawek.course.model.Course;

import java.util.List;

@AllArgsConstructor
@Service
public class NotificationService {

    private final RabbitTemplate rabbitTemplate;

    public void sendNotification(Course course) {
        rabbitTemplate.setExchange("Student");
        rabbitTemplate.setRoutingKey("enroll_finish");

        NotificationInfoDto notificationInfoDto = NotificationInfoDto.builder()
                .emails(getEmails(course))
                .courseId(course.getId())
                .courseName(course.getName())
                .courseDescription(course.getDescription())
                .courseStart(course.getStartDate())
                .courseEnd(course.getEndDate())
                .build();

        rabbitTemplate.convertAndSend(notificationInfoDto);
    }

    private List<String> getEmails(Course course) {
        return course.getStudents()
                .stream()
                .map(StudentMember::getEmail)
                .toList();
    }

}