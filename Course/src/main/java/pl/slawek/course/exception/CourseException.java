package pl.slawek.course.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseException extends RuntimeException {

    private CourseError courseError;

}
