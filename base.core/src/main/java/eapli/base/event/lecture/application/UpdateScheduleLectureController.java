package eapli.base.event.lecture.application;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.domain.LectureParticipant;
import eapli.base.event.lecture.repositories.LectureParticipantRepository;
import eapli.base.event.lecture.repositories.LectureRepository;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
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

    private final LectureParticipantRepository lectureParticipantRepository;

    private LectureParticipant lectureParticipant;

    private Lecture lecture;

    private final TimeTableService srv;



    public UpdateScheduleLectureController(){
        lectureRepository = PersistenceContext.repositories().lectures();
        userRepository = PersistenceContext.repositories().users();
        patternRepository = PersistenceContext.repositories().recurringPatterns();
        teacherRepository = PersistenceContext.repositories().teachers();

        lectureParticipantRepository = PersistenceContext.repositories().lectureParticipants();

        lecture = null;

        srv = new TimeTableService();
    }


    public Iterable<Lecture> listOfLecturesTaughtByTeacher()
    {

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> user = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());
        Optional<Teacher> teacher = teacherRepository.findBySystemUser(user.get());


        return lectureRepository.lectureGivenBy(teacher.get());
    }

    public Optional<Lecture> updateDateOfLecture(Lecture lecture, LocalDate removedDate, LocalDate newDate,LocalTime newStartTime,
                                                 int newDurationMinutes)
    {

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> user = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());
        Optional<Teacher> teacher = teacherRepository.findBySystemUser(user.get());

        if (teacher.isPresent())
        {
            pattern = lecture.pattern();

            /*now the recurring pattern of this specific lecture has one exception
            the recurring pattern of this lecture doesnt change, but as one exception
            for a specific day of a specific week*/
            if (pattern.addException(removedDate))
            {
                    patternRepository.save(pattern);

                    if(schedule(lecture,newDate,newStartTime,newDurationMinutes))
                        return Optional.of(this.lecture);
                    else
                        return Optional.empty();
            }

        }

        return Optional.empty();

    }


    private boolean schedule(Lecture lecture, LocalDate newDate,LocalTime newStartTime,
                            int newDurationMinutes)
    {
        Iterable<LectureParticipant> lectureParticipants = lectureParticipantRepository.lectureParticipants(lecture);

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> sysUser = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        Optional<SystemUser> user = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());
        Optional<Teacher> teacher = teacherRepository.findBySystemUser(user.get());

        ArrayList<SystemUser> present = new ArrayList<>();

        if (sysUser.isEmpty())
            return false;

        RecurringPattern newPattern = onceBuildPattern(newDate,newStartTime,newDurationMinutes);
        newPattern = patternRepository.save(newPattern);

        //check availability for teacher not for students
        if (srv.checkAvailabilityByUser(sysUser.get(), newPattern))
        {
            for(LectureParticipant lectureParticipant : lectureParticipants)
            {
                present.add(lectureParticipant.studentParticipant().user());
            }

            Lecture lec = new Lecture(teacher.get(),newPattern);


            //update student schedule
            srv.schedule(present,newPattern);

            //update teacher schedule
            srv.scheduleTeacher(sysUser.get(),newPattern);

            this.lecture = lectureRepository.save(lec);

            return true;
        }

        return false;
    }


    private RecurringPattern onceBuildPattern(LocalDate newDate, LocalTime startTime, int durationMinutes) {
        RecurringPatternFreqOnceBuilder builder =  new RecurringPatternFreqOnceBuilder();
        builder.withDate(newDate);
        builder.withDuration(startTime,durationMinutes);
        return builder.build();
    }


}
