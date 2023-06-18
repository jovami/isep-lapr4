package eapli.base.event.lecture.application;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.repositories.LectureRepository;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqWeeklyBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@UseCaseController
public class ScheduleLectureController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final LectureRepository lectureRepository;
    private final RecurringPatternRepository patternRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StaffRepository staffRepository;
    private final ScheduleLectureService svc;
    private final MyUserService userSvc;

    public ScheduleLectureController() {
        lectureRepository = PersistenceContext.repositories().lectures();
        patternRepository = PersistenceContext.repositories().recurringPatterns();
        enrollmentRepository = PersistenceContext.repositories().enrollments();
        staffRepository = PersistenceContext.repositories().staffs();
        svc = new ScheduleLectureService();
        this.userSvc = new MyUserService();
    }

    public boolean schedule(LocalDate startDate, LocalDate endDate, LocalTime startTime, int durationMinutes,
            Iterable<Enrollment> enrolled) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);

        var teacher = this.userSvc.currentTeacher();

        var pattern = buildPattern(startDate, endDate, startTime, durationMinutes);

        pattern = patternRepository.save(pattern);
        if (pattern == null) {
            return false;
        }

        var participants = new ArrayList<SystemUser>();
        enrolled.forEach(e -> {
            participants.add(e.student().user());
        });

        Lecture lecture = new Lecture(teacher, pattern);

        SystemUser user;

        // TODO: try-catch needed? Or abort to menu/UI?
        try {
            user = this.userSvc.currentUser();
        } catch (IllegalStateException e) {
            return false;
        }

        var organizer = user;

        if (!this.svc.scheduleLecture(organizer, participants, lecture)) {
            return false;
        }

        lectureRepository.save(lecture);

        return true;
    }

    private RecurringPattern buildPattern(LocalDate startDate, LocalDate endDate, LocalTime startTime,
            int durationMinutes) {
        return new RecurringPatternFreqWeeklyBuilder()
                .withDayOfWeek(startDate.getDayOfWeek())
                .withDuration(startTime, durationMinutes)
                .withDateInterval(startDate, endDate)
                .build();
    }

    public Iterable<Course> getCourses() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        var teacher = this.userSvc.currentTeacher();
        return staffRepository.taughtBy(teacher);
    }

    public Iterable<Enrollment> enrollmentsByCourse(Course course) {
        return enrollmentRepository.enrollmentsByCourse(course);
    }

}
