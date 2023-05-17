package eapli.base.enrollment.aplication;

import eapli.base.clientusermanagement.domain.users.MecanographicNumber;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.course.domain.Course;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class BulkEnrollStudentsService {

    private StudentRepository studentRepo;

    private CourseRepository courseRepo;

    private EnrollmentRepository enrollmentRepo;

    public BulkEnrollStudentsService(RepositoryFactory repos){
        studentRepo = repos.students();
        courseRepo = repos.courses();
        enrollmentRepo = repos.enrollments();

    }

    public void bulkEnroll(List<Pair<MecanographicNumber,Integer>> data)
    {
        for (Pair<MecanographicNumber,Integer> pair : data)
        {
            var mecanographicNumber = pair.getLeft();
            var courseID = pair.getRight();

            Student student = this.studentRepo.ofIdentity(mecanographicNumber).orElseThrow();
            Course course = this.courseRepo.ofIdentity(courseID).orElseThrow();

            Enrollment enrollment = new Enrollment(course,student);

            this.enrollmentRepo.save(enrollment);
        }
    }
}
