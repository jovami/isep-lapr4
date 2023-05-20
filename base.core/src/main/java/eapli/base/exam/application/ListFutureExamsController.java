package eapli.base.exam.application;

import static org.eclipse.collections.impl.block.factory.HashingStrategies.fromFunction;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.eclipse.collections.impl.factory.HashingStrategySets;

import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.exam.dto.FutureExamDTO;
import eapli.base.exam.dto.FutureExamDTOMapper;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

/**
 * ListFutureExamsController
 */
@UseCaseController
public final class ListFutureExamsController {

    private final AuthorizationService authz;

    private final StudentRepository studentRepo;
    private final EnrollmentRepository enrollRepo;
    private final RegularExamRepository examRepo;

    public ListFutureExamsController() {
        this.authz = AuthzRegistry.authorizationService();
        var repos = PersistenceContext.repositories();

        this.studentRepo = repos.students();
        this.enrollRepo = repos.enrollments();
        this.examRepo = repos.regularExams();
    }

    public List<FutureExamDTO> futureExams(LocalDateTime time) {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);

        var student = this.studentRepo.findBySystemUser(getUser())
                .orElseThrow(() -> new IllegalStateException("User not a student"));

        var courses = HashingStrategySets.mutable.withAll(
                fromFunction(Course::identity),
                this.enrollRepo.coursesOfEnrolledStudent(student));

        var exams = this.examRepo.examsOfCoursesAfterTime(time, courses);

        try {
            System.out.println(4);
            return new FutureExamDTOMapper().toDTO(exams);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // TODO: factor out common code
    private SystemUser getUser() {
        return this.authz.session()
                .orElseThrow(() -> new IllegalStateException("User not logged in"))
                .authenticatedUser();
    }
}
