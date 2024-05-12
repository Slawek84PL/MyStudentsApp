package pl.slawek.students.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionHandler {

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<ErrorInfo> handleException(StudentException e) {
        switch (e.getStudentError()) {
            case StudentError.STUDENT_NOT_FOUND -> {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(e.getStudentError().getMessage()));
            }
            case StudentError.EMAIL_EXIST -> {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo(e.getStudentError().getMessage()));
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorInfo(e.getStudentError().getMessage()));
    }
}