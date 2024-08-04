package pl.slawek.students.model.student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("student")
public class StudentView {

    private final StudentRepository studentRepository;

    @GetMapping
    public String getStudents() {
        return "main";
    }
}
