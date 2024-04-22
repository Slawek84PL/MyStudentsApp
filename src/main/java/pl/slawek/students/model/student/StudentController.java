package pl.slawek.students.model.student;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@RequestBody @Valid Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> putStudent(@PathVariable long id, @RequestBody @Valid Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    student.setId(id);
                    return ResponseEntity.ok(studentRepository.save(student));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable long id, @RequestBody @Valid Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    if (!student.getFirstName().isEmpty()) {
                        studentFromDb.setFirstName(student.getFirstName());
                    }
                    if (!student.getLastName().isEmpty()) {
                        studentFromDb.setLastName(student.getLastName());
                    }
                    return ResponseEntity.ok(studentRepository.save(studentFromDb));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
