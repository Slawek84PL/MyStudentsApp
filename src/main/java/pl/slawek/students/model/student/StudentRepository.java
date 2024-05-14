package pl.slawek.students.model.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    List<Student> findByStatus(Status status);

    boolean existsByEmail(String email);


}