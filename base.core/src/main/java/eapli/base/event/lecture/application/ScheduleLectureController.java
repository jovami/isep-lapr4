package eapli.base.event.lecture.application;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.dto.StudentUsernameMecanographicNumberDTO;
import eapli.base.clientusermanagement.dto.StudentUsernameMecanographicNumberDTOMapper;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.domain.LectureParticipant;
import eapli.base.event.lecture.repositories.LectureParticipantRepository;
import eapli.base.event.lecture.repositories.LectureRepository;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqWeeklyBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.base.event.timetable.application.TimeTableService;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ScheduleLectureController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    //Repositories
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final RecurringPatternRepository patternRepository;
    private final LectureParticipantRepository participantRepository;
    //Service
    private final TimeTableService srv;
    //Domain
    private Lecture lecture;
    private ArrayList<SystemUser> invited= new ArrayList<>();

    private ArrayList<Student> students;

    private RecurringPattern pattern;

    public ScheduleLectureController(){
        lectureRepository = PersistenceContext.repositories().lectures();
        userRepository = PersistenceContext.repositories().users();
        patternRepository = PersistenceContext.repositories().recurringPatterns();
        participantRepository = PersistenceContext.repositories().lectureParticipants();
        teacherRepository = PersistenceContext.repositories().teachers();
        studentRepository = PersistenceContext.repositories().students();
        students =(ArrayList<Student>) studentRepository.findAll();

        srv = new TimeTableService();
    }

    public boolean createLecture(LocalDate startDate,LocalDate endDate, LocalTime startTime, int durationMinutes){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> user = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());
        Optional<Teacher> teacher = teacherRepository.findBySystemUser(user.get());


        pattern = buildPattern(startDate,endDate,startTime,durationMinutes);
        pattern = patternRepository.save(pattern);

        if (pattern!=null){
            if(teacher.isPresent()){
                lecture = new Lecture(teacher.get(), pattern);
                this.lecture = lectureRepository.save(lecture);
                return true;

            }
        }
        return false;
    }

    private RecurringPattern buildPattern(LocalDate startDate,LocalDate endDate, LocalTime startTime, int durationMinutes) {
        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDayOfWeek(startDate.getDayOfWeek());
        builder.withDuration(startTime,durationMinutes);
        builder.withDateInterval(startDate,endDate);
        return builder.getPattern();
    }


    public boolean schedule(){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> sysUser = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());

        if(srv.checkAvailabilityByUser(sysUser.get(),lecture.pattern())){
            //create LectureParticipant for each invited user

            for (SystemUser user: invited) {
                //TODO:check ManytoOne participant-> Lecture
                //TODO: transaction??

                LectureParticipant participant = new LectureParticipant(findStudentBySystemUser(user),lecture);
                participantRepository.save(participant);
            }

            if (!srv.schedule(invited, lecture.pattern())) {
                return false;
            }
            return true;
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
        return new StudentUsernameMecanographicNumberDTOMapper().toDTO(students, Comparator.comparing(Student::identity));
    }

    public boolean inviteStudent(StudentUsernameMecanographicNumberDTO dto){
        Student student = fromStudentDTO(dto);
        if(invited.contains(student.user())){
            return false;
        }else {
            invited.add(student.user());
            students.remove(student);
            return true;
        }
    }
    private Student findStudentBySystemUser(SystemUser user){
        return studentRepository.findByUsername(user.username()).get();
    }
}

