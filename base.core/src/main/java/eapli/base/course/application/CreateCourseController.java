package eapli.base.course.application;

import java.time.LocalDate;

import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseDescription;
import eapli.base.course.domain.CourseDuration;
import eapli.base.course.domain.CourseName;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public class CreateCourseController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CourseRepository repo;
    private Course course;
    private int id;

    public CreateCourseController() {
        repo = PersistenceContext.repositories().courses();
        course = null;
    }

    public boolean createCourse(String name, String description, LocalDate startDate, LocalDate endDate) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MANAGER);
        try {
            course = new Course(CourseName.valueOf(name), CourseDescription.valueOf(description), CourseDuration.valueOf(startDate, endDate));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean addCapacity(int min, int max) {
        return course.setCapacity(min, max);
    }

    public boolean saveCourse() {
        if (course == null) {
            return false;
        }
        course = this.repo.save(course);
        return true;
    }

    public long countAll() {
        return repo.size();
    }

    public String courseString() {
        return course.toString();
    }
}
