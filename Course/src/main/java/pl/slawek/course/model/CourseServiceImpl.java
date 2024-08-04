package pl.slawek.course.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.slawek.course.StudentServiceClient;
import pl.slawek.course.dto.Student;
import pl.slawek.course.dto.StudentMember;
import pl.slawek.course.exception.CourseException;
import pl.slawek.course.notification.NotificationService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;
import static pl.slawek.course.exception.CourseError.COURSE_INACTIVE_CONFLICT;
import static pl.slawek.course.exception.CourseError.COURSE_NOT_FOUND;
import static pl.slawek.course.exception.CourseError.INCORRECT_STATUS;
import static pl.slawek.course.exception.CourseError.COURSE_STATUS_INACTIVE;
import static pl.slawek.course.exception.CourseError.STUDENT_ALREADY_EXIST;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentServiceClient studentServiceClient;
    private final NotificationService notificationService;

    @Override
    public List<Course> getCourses(String status) {
        if (!status.isEmpty() && status != null) {
            try {
                Status statusEnum = Status.valueOf(status.toUpperCase());
                return courseRepository.findByStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                throw new CourseException(INCORRECT_STATUS);
            }
        }
        return courseRepository.findAll();
    }

    @Override
    public Course getCourse(String id) {
        Course course = getOneCourse(id);
        if (Status.INACTIVE.equals(course.getStatus())) {
            throw new CourseException(COURSE_STATUS_INACTIVE);
        }
        return course;
    }

    @Override
    public void deleteCourse(String id) {
        Course course = getOneCourse(id);
        course.setStatus(Status.INACTIVE);
        courseRepository.save(course);
    }

    @Override
    public Course putCourse(String id, Course course) {
        return courseRepository.findById(id)
                .map(courseFromDb -> {
                    courseFromDb.setName(course.getName());
                    courseFromDb.setDescription(course.getDescription());
                    courseFromDb.setStartDate(course.getStartDate());
                    courseFromDb.setEndDate(course.getEndDate());
                    courseFromDb.setParticipantsLimit(course.getParticipantsLimit());
                    courseFromDb.setParticipantsNumber(course.getParticipantsNumber());
                    courseFromDb.setStatus(course.getStatus());
                    courseRepository.save(courseFromDb);
                    return courseFromDb;
                }).orElseThrow(() -> new CourseException(COURSE_NOT_FOUND));

    }

    @Override
    public Course pathCourse(String id, Course course) {
        return courseRepository.findById(id)
                .map(courseFromDb -> {
                    if (!isEmpty(course.getName())) {
                        courseFromDb.setName(course.getName());
                    }
                    if (!isEmpty(course.getDescription())) {
                        courseFromDb.setDescription(course.getDescription());
                    }
                    if (!isEmpty(course.getStartDate())) {
                        courseFromDb.setStartDate(course.getStartDate());
                    }
                    if (!isEmpty(course.getEndDate())) {
                        courseFromDb.setEndDate(course.getEndDate());
                    }
                    if (course.getParticipantsLimit() >= 0) {
                        courseFromDb.setParticipantsLimit(course.getParticipantsLimit());
                    }
                    if (course.getParticipantsNumber() >= 0) {
                        courseFromDb.setParticipantsNumber(course.getParticipantsNumber());
                    }
                    if (course.getStatus() != null) {
                        courseFromDb.setStatus(course.getStatus());
                    }
                    courseFromDb.validateCourse();
                    courseRepository.save(courseFromDb);
                    return courseFromDb;
                }).orElseThrow(() -> new CourseException(COURSE_NOT_FOUND));
    }

    private Course getOneCourse(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseException(COURSE_NOT_FOUND));
    }

    @Override
    public Course addCourse(Course course) {
        course.validateCourse();
        return courseRepository.save(course);
    }

    @Transactional
    @Override
    public void addStudent(String courseId, long studentId) {
        Course course = getCourse(courseId);
        validateCourseStatus(course);
        Student student = studentServiceClient.getStudent(studentId);

        validateStudentBeforeAdd(student, course);

        StudentMember studentMember = new StudentMember(student.getEmail());

        course = course.addStudent(studentMember);

        courseRepository.save(course);
    }

    @Override
    public List<Student> getStudentsMembers(String courseId) {
        Course course = getCourse(courseId);
        List<String> emails = new ArrayList<>();
        course.getStudents().forEach(
                studentMember -> {
                    emails.add(studentMember.getEmail());
                }
        );
        return studentServiceClient.getStudentByEmail(emails);
    }

    @Override
    public void closeCourse(String courseId) {
        Course course = getOneCourse(courseId);
        if (Status.INACTIVE.equals(course.getStatus())) {
            throw new CourseException(COURSE_INACTIVE_CONFLICT);
        }
        course.setStatus(Status.INACTIVE);
        notificationService.sendNotification(courseId);
        courseRepository.save(course);
    }

    private void validateStudentBeforeAdd(Student student, Course course) {
        if (!Status.ACTIVE.equals(student.getStatus())) {
            throw new CourseException(COURSE_STATUS_INACTIVE);
        }

        if (course.getStudents().stream()
                .anyMatch(studentEmail ->
                        studentEmail.getEmail().equals(student.getEmail()))) {
            throw new CourseException(STUDENT_ALREADY_EXIST);
        }
    }

    private void validateCourseStatus(Course course) {
        if (!Status.ACTIVE.equals(course.getStatus())) {
            throw new CourseException(COURSE_STATUS_INACTIVE);
        }
    }
}
