package pl.slawek.course;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.slawek.course.dto.Student;

import java.util.List;

@FeignClient(name = "student-service", path = "students")
public interface StudentServiceClient {

    @GetMapping("{studentId}")
    Student getStudent(@PathVariable long studentId);

    @PostMapping("/emails")
    List<Student> getStudentByEmail(@RequestBody List<String> emails);
}