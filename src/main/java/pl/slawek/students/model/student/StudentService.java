package pl.slawek.students.model.student;

import java.util.List;

public interface StudentService {

    List<Student> getStudents(String status);

    Student addStudent(Student student);

    void deleteStudent(Long id);

    Student putStudent(Long id, Student student);

    Student patchStudent(Long id, Student student);

    Student getStudent(long id);
}
