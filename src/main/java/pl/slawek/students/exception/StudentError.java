package pl.slawek.students.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.slawek.students.model.student.Status;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum StudentError {

    STUDENT_NOT_FOUND("Student dose not exist"),
    EMAIL_EXIST("Email exist in database"),
    INCORRECT_STATUS("Incorrect status of student. Use option " + Arrays.toString(Status.values())),
    STUDENT_INACTIVE("Student is not active");

    private final String message;
}
