package eapli.base.enrollment.repositories;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.enrollment.domain.Enrollment;
import eapli.framework.domain.repositories.DomainRepository;

public interface EnrollmentRepository extends DomainRepository<Integer, Enrollment> {

    Iterable<Course> coursesOfEnrolledStudent(Student s);
    Iterable<Enrollment> enrollmentsByCourse(Course c);
    Iterable<Student> studentsOfEnrolledCourse(Course c);
    Iterable<Course> ongoingCoursesOfStudent(Student s);
}
