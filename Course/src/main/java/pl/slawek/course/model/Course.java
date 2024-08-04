package pl.slawek.course.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.slawek.course.dto.StudentMember;
import pl.slawek.course.exception.CourseException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static pl.slawek.course.exception.CourseError.COURSE_CAN_NOT_SET_ACTIVE_STATUS;
import static pl.slawek.course.exception.CourseError.COURSE_CAN_NOT_SET_FULL_STATUS;
import static pl.slawek.course.exception.CourseError.COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED;
import static pl.slawek.course.exception.CourseError.COURSE_START_DATE_IS_AFTER_END_DATE;

@Getter
@Setter

@Document(collection = "courses")
public class Course {

    @Id
    private String id;

    @NotBlank
    private String name;
    private String description;

    @NotNull
    @Future
    private LocalDateTime startDate;

    @NotNull
    @Future
    private LocalDateTime endDate;

    @NotNull
    @Min(0)
    private Long participantsNumber;

    @NotNull
    @Min(0)
    private Long participantsLimit;

    @Enumerated(EnumType.STRING)
    private Status status;

    private List<StudentMember> students = new ArrayList<>();

    void validateCourse() {
        validateCourseDate();
        validateParticipantsLimit();
        validateFullStatus();
    }

    private void validateCourseDate() {
        if (startDate.isAfter(endDate)) {
            throw new CourseException(COURSE_START_DATE_IS_AFTER_END_DATE);
        }
    }

    private void validateParticipantsLimit() {
        if (participantsNumber > participantsLimit) {
            throw new CourseException(COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED);
        }
    }

    private void validateFullStatus() {
        if (Status.FULL.equals(status) && !participantsNumber.equals(participantsLimit)) {
            throw new CourseException(COURSE_CAN_NOT_SET_FULL_STATUS);
        }
        if (Status.ACTIVE.equals(status) && participantsNumber.equals(participantsLimit)) {
            throw new CourseException(COURSE_CAN_NOT_SET_ACTIVE_STATUS);
        }
    }

    public Course addStudent(StudentMember student) {
        validateParticipantsLimit();
        this.students.add(student);
        participantsNumber = (long) students.size();

        if (participantsLimit.equals(participantsNumber)) {
            status = Status.FULL;
        }
        return this;
    }
}
