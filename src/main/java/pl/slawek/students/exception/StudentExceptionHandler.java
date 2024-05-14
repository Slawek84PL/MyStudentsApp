package pl.slawek.students.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionHandler {

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<ErrorInfo> handleException(StudentException e) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        switch (e.getStudentError()) {
            case STUDENT_NOT_FOUND -> httpStatus = HttpStatus.NOT_FOUND;
            case EMAIL_EXIST -> httpStatus = HttpStatus.CONFLICT;
            case INCORRECT_STATUS -> httpStatus = HttpStatus.NOT_EXTENDED;
            case STUDENT_INACTIVE -> httpStatus = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getStudentError().getMessage()));
    }
}