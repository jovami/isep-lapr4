package eapli.base.event.lecture.application;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.domain.LectureParticipant;
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
import java.util.Optional;

@UseCaseController
public class ScheduleLectureController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final TransactionalContext txCtx = PersistenceContext.repositories()
            .newTransactionalContext();

    // Repositories
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final RecurringPatternRepository patternRepository;
    private final LectureParticipantRepository participantRepository;
    private StaffRepository staffRepository;

    private final EnrollmentRepository enrollmentRepository;

    // Service
    private final TimeTableService srv;
    // Domain
    private Lecture lecture;
    private ArrayList<Student> students;
    private RecurringPattern pattern;
    private ArrayList<SystemUser> present = new ArrayList<>();
    private Course course;

    public ScheduleLectureController() {
        lectureRepository = PersistenceContext.repositories().lectures();
        userRepository = PersistenceContext.repositories().users();
        patternRepository = PersistenceContext.repositories().recurringPatterns();
        participantRepository = PersistenceContext.repositories().lectureParticipants();
        teacherRepository = PersistenceContext.repositories().teachers();
        studentRepository = PersistenceContext.repositories().students();
        enrollmentRepository = PersistenceContext.repositories().enrollments();
        staffRepository = PersistenceContext.repositories().staffs();
        students = (ArrayList<Student>) studentRepository.findAll();

        srv = new TimeTableService();
    }

    public boolean createLecture(LocalDate startDate, LocalDate endDate, LocalTime startTime, int durationMinutes,Iterable<Enrollment> enrolled) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> user = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());
        Optional<Teacher> teacher = teacherRepository.findBySystemUser(user.get());

        pattern = buildPattern(startDate, endDate, startTime, durationMinutes);
        txCtx.beginTransaction();
        pattern = patternRepository.save(pattern);

        if (pattern != null) {
            if (teacher.isPresent()) {
                lecture = new Lecture(teacher.get(), pattern);
                this.lecture = lectureRepository.save(lecture);
                if (schedule(enrolled)) {
                    txCtx.commit();
                    return true;
                }
            }
        }
        txCtx.rollback();
        return false;
    }

    public Teacher getSessionTeacher() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> user = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());
        Optional<Teacher> teacher = teacherRepository.findBySystemUser(user.get());
        return teacher.get();
    }

    private RecurringPattern buildPattern(LocalDate startDate, LocalDate endDate, LocalTime startTime,
            int durationMinutes) {
        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDayOfWeek(startDate.getDayOfWeek());
        builder.withDuration(startTime, durationMinutes);
        builder.withDateInterval(startDate, endDate);
        return builder.build();
    }

    public boolean schedule(Iterable<Enrollment> enrolled) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> sysUser = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());

        if (sysUser.isEmpty())
            return false;

        SystemUser teacher = sysUser.get();

        if (srv.checkAvailabilityByUser(teacher, lecture.pattern())) {
            // create LectureParticipant for each invited user
            for (Enrollment enroll : enrolled) {
                LectureParticipant participant = new LectureParticipant(enroll.student(), lecture);
                present.add(enroll.student().user());
                participantRepository.save(participant);
            }

            if (srv.schedule(present, lecture.pattern()) && srv.schedule(teacher, lecture.pattern())) {
                System.out.println("Lecture: \n" + lectureRepository.findAll().toString());
                System.out.println("Participants: \n" + participantRepository.findAll().toString());
                return true;
            }
        }
        return false;
    }

    public Iterable<Course> coursesTaughtBy(Teacher teacher) {
        return staffRepository.taughtBy(teacher);
    }

    public Iterable<Enrollment> enrollmentsByCourse(Course course) {
        return enrollmentRepository.enrollmentsByCourse(course);
    }

}
