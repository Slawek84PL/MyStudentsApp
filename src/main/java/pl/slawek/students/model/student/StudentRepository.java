package pl.slawek.students.model.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    List<Student> findByStatus(Status status);

    boolean existsByEmail(String email);

    Student findByEmail(String email);
}