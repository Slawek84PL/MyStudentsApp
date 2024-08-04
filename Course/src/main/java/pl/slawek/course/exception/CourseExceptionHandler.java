package pl.slawek.course.exception;

import feign.FeignException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CourseExceptionHandler {

    @ExceptionHandler(CourseException.class)
    public ResponseEntity<ErrorInfo> handleException(CourseException e) {

        HttpStatus httpStatus = INTERNAL_SERVER_ERROR;

        switch (e.getCourseError()) {
            case COURSE_NOT_FOUND -> httpStatus = NOT_FOUND;
            case INCORRECT_STATUS -> httpStatus = BAD_REQUEST;
            case COURSE_STATUS_INACTIVE -> httpStatus = BAD_REQUEST;
            case COURSE_START_DATE_IS_AFTER_END_DATE -> httpStatus = BAD_REQUEST;
            case COURSE_PARTICIPANTS_LIMIT_IS_EXCEEDED -> httpStatus = CONFLICT;
            case COURSE_CAN_NOT_SET_FULL_STATUS -> httpStatus = CONFLICT;
            case COURSE_CAN_NOT_SET_ACTIVE_STATUS -> httpStatus = CONFLICT;
            case STUDENT_ALREADY_EXIST -> httpStatus = CONFLICT;
            case COURSE_INACTIVE_CONFLICT -> httpStatus = CONFLICT;
        }

        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getCourseError().getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeignException(FeignException e) {
        return ResponseEntity.status(e.status()).body(new JSONObject(e.contentUTF8()).toMap());
    }
}