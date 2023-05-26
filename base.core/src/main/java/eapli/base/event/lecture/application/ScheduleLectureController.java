package eapli.base.event.lecture.application;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.repositories.LectureParticipantRepository;
import eapli.base.event.lecture.repositories.LectureRepository;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqWeeklyBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.base.event.timetable.application.TimeTableService;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@UseCaseController
public class ScheduleLectureController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final TransactionalContext txCtx;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final RecurringPatternRepository patternRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StaffRepository staffRepository;
    private final ScheduleLectureService svc;

    public ScheduleLectureController() {
        txCtx = PersistenceContext.repositories().newTransactionalContext();
        lectureRepository = PersistenceContext.repositories().lectures();
        userRepository = PersistenceContext.repositories().users();
        patternRepository = PersistenceContext.repositories().recurringPatterns();
        teacherRepository = PersistenceContext.repositories().teachers();
        enrollmentRepository = PersistenceContext.repositories().enrollments();
        staffRepository = PersistenceContext.repositories().staffs();
        svc = new ScheduleLectureService();
    }

    public boolean schedule(LocalDate startDate, LocalDate endDate, LocalTime startTime, int durationMinutes, Iterable<Enrollment> enrolled) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        var sessionOpt = authz.session();
        if (sessionOpt.isEmpty()) {
            return false;
        }
        var session = sessionOpt.get();

        var teacher = teacherRepository.findBySystemUser(session.authenticatedUser());
        if (teacher.isEmpty()) {
            System.out.println("Teacher not found.");
            return false;
        }

        var pattern = buildPattern(startDate, endDate, startTime, durationMinutes);
        txCtx.beginTransaction();

        pattern = patternRepository.save(pattern);
        if (pattern == null) {
            txCtx.rollback();
            return false;
        }

        var participants = new ArrayList<SystemUser>();
        enrolled.forEach(e -> {
            participants.add(e.student().user());
        });

        Lecture lecture = new Lecture(teacher.get(), pattern);

        var userOpt = userRepository.ofIdentity(session.authenticatedUser().identity());
        if (userOpt.isEmpty()) {
            txCtx.rollback();
            return false;
        }
        var organizer = userOpt.get();

        if (!this.svc.scheduleLecture(organizer, participants, lecture)) {
            txCtx.rollback();
            return false;
        }

        lectureRepository.save(lecture);
        txCtx.commit();

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
        var sessionOpt = authz.session();
        if (sessionOpt.isEmpty()) {
            System.out.println("Session not found.");
            throw new IllegalStateException();
        }
        var session = sessionOpt.get();

        var teacher = teacherRepository.findBySystemUser(session.authenticatedUser());
        if (teacher.isEmpty()) {
            System.out.println("Teacher not found.");
            throw new IllegalStateException();
        }

        return staffRepository.taughtBy(teacher.get());
    }

    public Iterable<Enrollment> enrollmentsByCourse(Course course) {
        return enrollmentRepository.enrollmentsByCourse(course);
    }

}
