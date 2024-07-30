package pl.slawek.students.model.student;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentServiceImpl studentService;

    @GetMapping(params = "status")
    public ResponseEntity<List<Student>> getStudents(@RequestParam(name = "status", required = false) String status) {
        return ResponseEntity.ok(studentService.getStudents(status));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@RequestBody @Valid Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("{id}")
    public Student getStudent(@PathVariable long id) {
        return studentService.getStudent(id);
    }

    @PostMapping("emails")
    public List<Student> getStudentsByEmails(@RequestBody List<String> emails) {
        return studentService.getStudentsByEmails(emails);
    }

    @PutMapping("{id}")
    public Student putStudent(@PathVariable long id, @RequestBody @Valid Student student) {
        return studentService.putStudent(id, student);
    }

    @PatchMapping("{id}")
    public Student patchStudent(@PathVariable long id, @RequestBody Student student) {
        return studentService.patchStudent(id, student);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
    }
}
