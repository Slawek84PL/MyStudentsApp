package pl.slawek.course.model;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.slawek.course.dto.Student;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<Course> getCourses(@RequestParam(required = false) String status) {
        return courseService.getCourses(status);
    }

    @PostMapping
    public Course addCourse(@Valid @RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @DeleteMapping("{id}")
    public void deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("{id}")
    public Course getCourse(@PathVariable String id) {
        return courseService.getCourse(id);
    }

    @PutMapping("{id}")
    public Course putCourse(@PathVariable String id, @Valid @RequestBody Course course) {
        return courseService.putCourse(id, course);
    }

    @PatchMapping("{id}")
    public Course pathCourse(@PathVariable String id, @RequestBody Course course) {
        return courseService.pathCourse(id, course);
    }

    @PostMapping(path = "add-student", params = {"courseId", "studentId"})
    public ResponseEntity<?> addStudent(
            @RequestParam(name = "courseId") String courseId,
            @RequestParam(name = "studentId") long studentId) {

        courseService.addStudent(courseId, studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/members")
    public List<Student> getStudents(@PathVariable String id) {
        return courseService.getStudentsMembers(id);
    }

    @PatchMapping("{id}/finish-enroll")
    public void closeCourse(@PathVariable String id) {
        courseService.closeCourse(id);
    }
}
