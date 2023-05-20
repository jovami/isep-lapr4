package eapli.base.event.lecture.application;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.dto.StudentUsernameMecanographicNumberDTO;
import eapli.base.clientusermanagement.dto.StudentUsernameMecanographicNumberDTOMapper;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.domain.LectureParticipant;
import eapli.base.event.lecture.repositories.LectureParticipantRepository;
import eapli.base.event.lecture.repositories.LectureRepository;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.base.event.timetable.application.TimeTableService;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.io.util.Console;

@UseCaseController
public class ScheduleExtraLectureController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    // Repositories
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final RecurringPatternRepository patternRepository;
    private final LectureParticipantRepository participantRepository;

    // Service
    private final TimeTableService srv;

    // Domain
    private Lecture lecture;
    private final ArrayList<SystemUser> invited = new ArrayList<>();
    private final ArrayList<Student> students;
    private RecurringPattern pattern;

    public ScheduleExtraLectureController() {
        lectureRepository = PersistenceContext.repositories().lectures();
        userRepository = PersistenceContext.repositories().users();
        patternRepository = PersistenceContext.repositories().recurringPatterns();
        participantRepository = PersistenceContext.repositories().lectureParticipants();
        teacherRepository = PersistenceContext.repositories().teachers();
        studentRepository = PersistenceContext.repositories().students();
        students = (ArrayList<Student>) studentRepository.findAll();

        srv = new TimeTableService();
    }

    public boolean createLecture(LocalDate date, LocalTime time, int durationMinutes) {
        LocalDateTime lectureDateTime = LocalDateTime.of(date, time);
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (lectureDateTime.isBefore(currentDateTime)) {
            System.out.println("Unable to create lecture. The specified date and time have already passed.");
            return false;
        }

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        var session = authz.session();
        if (session.isEmpty()) {
            return false;
        }

        var teacher = teacherRepository.findBySystemUser(session.get().authenticatedUser());
        if (teacher.isEmpty()) {
            System.out.println("Student not found.");
            return false;
        }

        pattern = buildPattern(date, time, durationMinutes);
        pattern = patternRepository.save(pattern);
        if (pattern == null) {
            return false;
        }

        lecture = new Lecture(teacher.get(), pattern);
        this.lecture = lectureRepository.save(lecture);
        return true;
    }

    private RecurringPattern buildPattern(LocalDate date, LocalTime time, int durationMinutes) {
        RecurringPatternFreqOnceBuilder builder = new RecurringPatternFreqOnceBuilder();
        builder.withDate(date);
        builder.withDuration(time, durationMinutes);

        return builder.build();
    }

    // TODO: check ManyToOne participant -> Lecture
    // TODO: transaction?
    public boolean schedule() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        var session = authz.session();
        if (session.isEmpty()) {
            return false;
        }

        var user = userRepository.ofIdentity(session.get().authenticatedUser().identity());
        if (srv.checkAvailabilityByUser(user.orElseThrow(), lecture.pattern())) {
            for (SystemUser sysUser : invited) {
                LectureParticipant participant = new LectureParticipant(findStudentBySystemUser(sysUser), lecture);
                participantRepository.save(participant);
            }

            return srv.schedule(invited, lecture.pattern());
        }

        patternRepository.delete(pattern);
        lectureRepository.delete(lecture);
        return false;
    }

    private Student fromStudentDTO(StudentUsernameMecanographicNumberDTO dto) throws ConcurrencyException {
        return this.studentRepository.ofIdentity(dto.mecanographicNumber())
                .orElseThrow(() -> new ConcurrencyException("User no longer exists"));
    }

    public List<StudentUsernameMecanographicNumberDTO> students() {
        return new StudentUsernameMecanographicNumberDTOMapper().toDTO(students,
                Comparator.comparing(Student::identity));
    }

    public boolean inviteStudent(StudentUsernameMecanographicNumberDTO dto) {
        Student student = fromStudentDTO(dto);
        if (invited.contains(student.user())) {
            return false;
        } else {
            invited.add(student.user());
            students.remove(student);
            return true;
        }
    }

    private Student findStudentBySystemUser(SystemUser user) {
        return studentRepository.findByUsername(user.username()).orElseThrow();
    }

    public LocalDate readDate(final String prompt) {
        System.out.println(prompt);
        do {
            try {
                final int day = Console.readInteger("Day:");
                final int month = Console.readInteger("Month:");
                final int year = Console.readInteger("Year:");

                LocalDate enteredDate = LocalDate.of(year, month, day);
                LocalDate currentDate = LocalDate.now();

                if (enteredDate.isAfter(currentDate)) {
                    return enteredDate;
                } else {
                    System.out.println("Please enter a date after today.");
                }
            } catch (@SuppressWarnings("unused") final DateTimeException ex) {
                System.out.println("There was an error while parsing the given date");
            }
        } while (true);
    }

    public LocalTime readTime(final String prompt) {
        System.out.println(prompt);
        do {
            try {
                final int hour = Console.readInteger("Hour:");
                final int minute = Console.readInteger("Minute:");
                return LocalTime.of(hour, minute);

            } catch (@SuppressWarnings("unused") final DateTimeException ex) {
                System.out.println("There was an error while parsing the given time");
            }
        } while (true);
    }
}
