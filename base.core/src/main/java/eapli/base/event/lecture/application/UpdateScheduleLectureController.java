package eapli.base.event.lecture.application;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.repositories.LectureParticipantRepository;
import eapli.base.event.lecture.repositories.LectureRepository;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqWeeklyBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.base.event.timetable.application.TimeTableService;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class UpdateScheduleLectureController {


    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    //Repositories
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final RecurringPatternRepository patternRepository;
    private RecurringPattern pattern;

    private Lecture lecture;



    public UpdateScheduleLectureController(){
        lectureRepository = PersistenceContext.repositories().lectures();
        userRepository = PersistenceContext.repositories().users();
        patternRepository = PersistenceContext.repositories().recurringPatterns();
        teacherRepository = PersistenceContext.repositories().teachers();

        lecture = null;
    }


    public Iterable<Lecture> listOfLecturesTaughtByTeacher()
    {

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> user = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());
        Optional<Teacher> teacher = teacherRepository.findBySystemUser(user.get());


        return lectureRepository.lectureGivenBy(teacher.get());
    }

    public boolean updateDateOfLecture(Lecture lecture, LocalDate startDate, LocalDate endDate)
    {

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> user = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());
        Optional<Teacher> teacher = teacherRepository.findBySystemUser(user.get());


        pattern = buildPattern(startDate,endDate,lecture.pattern().startTime(),lecture.pattern().duration());
        pattern = patternRepository.save(pattern);

        if (pattern!=null){
            if(teacher.isPresent()){
                lecture.updatePattern(pattern);
                lectureRepository.save(lecture);
                this.lecture = lecture;
                return true;
            }
        }
        return false;
    }

    public boolean updateStartingTimeOfLecture(Lecture lecture, LocalTime startTime)
    {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> user = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());
        Optional<Teacher> teacher = teacherRepository.findBySystemUser(user.get());


        pattern = buildPattern(lecture.pattern().startDate(), lecture.pattern().endDate(),startTime,lecture.pattern().duration());
        pattern = patternRepository.save(pattern);

        if (pattern!=null){
            if(teacher.isPresent()){
                lecture.updatePattern(pattern);
                lectureRepository.save(lecture);
                this.lecture = lecture;
                return true;
            }
        }
        return false;
    }

    public boolean updateDurationOfLecture(Lecture lecture, int durationMinutes)
    {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> user = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());
        Optional<Teacher> teacher = teacherRepository.findBySystemUser(user.get());


        pattern = buildPattern(lecture.pattern().startDate(), lecture.pattern().endDate(),lecture.pattern().startTime(),durationMinutes);
        pattern = patternRepository.save(pattern);

        if (pattern!=null){
            if(teacher.isPresent()){
                lecture.updatePattern(pattern);
                lectureRepository.save(lecture);
                this.lecture = lecture;
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

    public String showLecture()
    {
        return this.lecture.toString();
    }

}
