package pl.slawek.course.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NotificationInfoDto{

    private List<String> emails;
    private String courseId;
    private String courseName;
    private String courseDescription;
    private LocalDateTime courseStart;
    private LocalDateTime courseEnd;
}