package pl.slawek.course.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.slawek.course.model.Status;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum CourseError {

    COURSE_NOT_FOUND("Course dose not exist"),
    INCORRECT_STATUS("Incorrect course status. Use option " + Arrays.toString(Status.values())),
    COURSE_STATUS_INACTIVE("Course status is not active"),
    COURSE_START_DATE_IS_AFTER_END_DATE("Course start date is after end date"),
    COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED("Course participants limit is exceeded"),
    COURSE_CAN_NOT_SET_FULL_STATUS("Course can't set full status"),
    COURSE_CAN_NOT_SET_ACTIVE_STATUS("Course can't set active status"),
    STUDENT_ALREADY_EXIST("Student already exist in this course"),
    COURSE_INACTIVE_CONFLICT("Course already inactive"),;
    private final String message;
}
