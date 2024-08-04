package pl.slawek.course.model;

import pl.slawek.course.dto.Student;

import java.util.List;


public interface CourseService {

    List<Course> getCourses(String status);

    Course getCourse(String id);

    Course addCourse(Course course);

    void deleteCourse(String id);

    Course putCourse(String id, Course course);

    Course pathCourse(String id, Course course);

    void addStudent(String courseId, long studentId);

    List<Student> getStudentsMembers(String courseId);

    void closeCourse(String courseId);
}
