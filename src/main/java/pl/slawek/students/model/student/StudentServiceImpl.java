package pl.slawek.students.model.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.slawek.students.exception.StudentError;
import pl.slawek.students.exception.StudentException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getStudents(String status) {
        if (status != null && !status.isEmpty()) {
            try {
                Status statusEnum = Status.valueOf(status.toUpperCase());
                return studentRepository.findByStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                throw new StudentException(StudentError.INCORRECT_STATUS);
            }
        }
        return studentRepository.findAll();
    }

    @Override
    public Student addStudent(Student student) {
        validateStudentEmailExist(student);
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        student.setStatus(Status.INACTIVE);
        studentRepository.save(student);
    }

    @Override
    public Student putStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    validateStudentEmailExist(student);
                    studentFromDb.setFirstName(student.getFirstName());
                    studentFromDb.setLastName(student.getLastName());
                    studentFromDb.setEmail(student.getEmail());
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    @Override
    public Student patchStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    if (!student.getFirstName().isEmpty()) {
                        studentFromDb.setFirstName(student.getFirstName());
                    }
                    if (!student.getLastName().isEmpty()) {
                        studentFromDb.setLastName(student.getLastName());
                    }
                    if(student.getStatus() != null) {
                        studentFromDb.setStatus(student.getStatus());
                    }

                    return studentRepository.save(studentFromDb);
                })
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    @Override
    public Student getStudent(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        if (student.getStatus() == Status.INACTIVE) {
            throw new StudentException(StudentError.STUDENT_INACTIVE);
        }
        return student;
    }

    private void validateStudentEmailExist(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new StudentException(StudentError.EMAIL_EXIST);
        }
    }
}
