package pl.slawek.students.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StudentError {

    STUDENT_NOT_FOUND("Student dose not exist");

    private String message;
}
