package eapli.base.event.lecture.application;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.dto.StudentUsernameMecanographicNumberDTO;
import eapli.base.clientusermanagement.dto.StudentUsernameMecanographicNumberDTOMapper;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.repositories.LectureRepository;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@UseCaseController
public class ScheduleExtraLectureController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final LectureRepository lectureRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StaffRepository staffRepository;
    private final RecurringPatternRepository patternRepository;
    private final ScheduleLectureService svc;
    private final MyUserService userSvc;

    public ScheduleExtraLectureController() {
        lectureRepository = PersistenceContext.repositories().lectures();
        patternRepository = PersistenceContext.repositories().recurringPatterns();
        studentRepository = PersistenceContext.repositories().students();
        enrollmentRepository = PersistenceContext.repositories().enrollments();
        staffRepository = PersistenceContext.repositories().staffs();
        svc = new ScheduleLectureService();
        this.userSvc = new MyUserService();
    }

    private RecurringPattern buildPattern(LocalDate date, LocalTime time, int durationMinutes) {
        return new RecurringPatternFreqOnceBuilder()
                .withDate(date)
                .withDuration(time, durationMinutes)
                .build();
    }

    public boolean schedule(LocalDate date, LocalTime time, int duration,
            ArrayList<StudentUsernameMecanographicNumberDTO> participants) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);

        var teacher = this.userSvc.currentTeacher();

        var pattern = buildPattern(date, time, duration);

        pattern = patternRepository.save(pattern);
        if (pattern == null) {
            return false;
        }

        var invited = participants.stream().map(dto -> fromStudentDTO(dto).user()).collect(Collectors.toList());

        var lecture = new Lecture(teacher, pattern);

        var organizer = this.userSvc.currentUser();

        if (!this.svc.scheduleLecture(organizer, invited, lecture)) {
            return false;
        }

        lectureRepository.save(lecture);

        return true;
    }

    private Student fromStudentDTO(StudentUsernameMecanographicNumberDTO dto) throws ConcurrencyException {
        return this.studentRepository.ofIdentity(dto.mecanographicNumber())
                .orElseThrow(() -> new ConcurrencyException("User no longer exists"));
    }

    public List<StudentUsernameMecanographicNumberDTO> listStudents(Course course) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);

        return new StudentUsernameMecanographicNumberDTOMapper().toDTO(
                enrollmentRepository.studentsOfEnrolledCourse(course),
                Comparator.comparing(Student::identity));
    }

    public Iterable<Course> getCourses() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        return staffRepository.taughtBy(this.userSvc.currentTeacher());
    }
}
