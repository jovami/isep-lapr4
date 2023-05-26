package eapli.base.event.lecture.application;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.dto.StudentUsernameMecanographicNumberDTO;
import eapli.base.clientusermanagement.dto.StudentUsernameMecanographicNumberDTOMapper;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.repositories.LectureRepository;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@UseCaseController
public class ScheduleExtraLectureController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final TransactionalContext txCtx;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final RecurringPatternRepository patternRepository;
    private final ScheduleLectureService svc;

    public ScheduleExtraLectureController() {
        txCtx = PersistenceContext.repositories().newTransactionalContext();
        lectureRepository = PersistenceContext.repositories().lectures();
        userRepository = PersistenceContext.repositories().users();
        patternRepository = PersistenceContext.repositories().recurringPatterns();
        teacherRepository = PersistenceContext.repositories().teachers();
        studentRepository = PersistenceContext.repositories().students();
        svc = new ScheduleLectureService();
    }

    private RecurringPattern buildPattern(LocalDate date, LocalTime time, int durationMinutes) {
        return new RecurringPatternFreqOnceBuilder()
                .withDate(date)
                .withDuration(time, durationMinutes)
                .build();
    }

    public boolean schedule(LocalDate date, LocalTime time, int duration, ArrayList<StudentUsernameMecanographicNumberDTO> participants) {
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

        var pattern = buildPattern(date, time, duration);
        txCtx.beginTransaction();

        pattern = patternRepository.save(pattern);
        if (pattern == null) {
            return false;
        }

        var invited = participants.stream().map(dto -> fromStudentDTO(dto).user()).collect(Collectors.toList());

        var lecture = new Lecture(teacher.orElseThrow(), pattern);

        var userOpt = userRepository.ofIdentity(session.authenticatedUser().identity());
        var organizer = userOpt.orElseThrow();

        if (!this.svc.scheduleLecture(organizer, invited, lecture)) {
            txCtx.rollback();
            return false;
        }

        lectureRepository.save(lecture);
        txCtx.commit();

        return true;
    }

    private Student fromStudentDTO(StudentUsernameMecanographicNumberDTO dto) throws ConcurrencyException {
        return this.studentRepository.ofIdentity(dto.mecanographicNumber())
                .orElseThrow(() -> new ConcurrencyException("User no longer exists"));
    }

    public List<StudentUsernameMecanographicNumberDTO> listStudents() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        return new StudentUsernameMecanographicNumberDTOMapper().toDTO(
                studentRepository.findAll(),
                Comparator.comparing(Student::identity));
    }
}
