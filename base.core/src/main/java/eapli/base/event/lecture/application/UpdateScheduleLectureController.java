package eapli.base.event.lecture.application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import eapli.base.clientusermanagement.application.MyUserService;
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
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public class UpdateScheduleLectureController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    // Repositories
    private final LectureRepository lectureRepository;
    private final RecurringPatternRepository patternRepository;
    private RecurringPattern pattern;

    private final LectureParticipantRepository lectureParticipantRepository;

    // private LectureParticipant lectureParticipant; TODO: unused?

    private Lecture lecture;

    private final TimeTableService srv;
    private final MyUserService userSvc;

    public UpdateScheduleLectureController() {
        lectureRepository = PersistenceContext.repositories().lectures();
        patternRepository = PersistenceContext.repositories().recurringPatterns();

        lectureParticipantRepository = PersistenceContext.repositories().lectureParticipants();

        lecture = null;

        srv = new TimeTableService();
        this.userSvc = new MyUserService();
    }

    public Iterable<Lecture> listOfLecturesTaughtByTeacher() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        return lectureRepository.lectureGivenBy(this.userSvc.currentTeacher());
    }

    public Optional<Lecture> updateDateOfLecture(Lecture lecture, LocalDate removedDate, LocalDate newDate,
            LocalTime newStartTime,
            int newDurationMinutes) {

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        @SuppressWarnings("unused") var teacher = this.userSvc.currentTeacher();

        pattern = lecture.pattern();

        /*
         * now the recurring pattern of this specific lecture has one exception
         * the recurring pattern of this lecture doesnt change, but as one exception
         * for a specific day of a specific week
         */
        if (pattern.addException(removedDate)) {
            patternRepository.save(pattern);

            if (schedule(lecture, newDate, newStartTime, newDurationMinutes))
                return Optional.of(this.lecture);
            else
                return Optional.empty();
        }

        return Optional.empty();

    }

    private boolean schedule(Lecture lecture, LocalDate newDate, LocalTime newStartTime,
            int newDurationMinutes) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);

        Iterable<LectureParticipant> lectureParticipants = lectureParticipantRepository.lectureParticipants(lecture);

        var sysUser = this.userSvc.currentUser();

        var teacher = this.userSvc.currentTeacher();

        ArrayList<SystemUser> present = new ArrayList<>();


        RecurringPattern newPattern = onceBuildPattern(newDate, newStartTime, newDurationMinutes);
        newPattern = patternRepository.save(newPattern);

        // check availability for teacher not for students
        if (srv.checkAvailabilityByUser(sysUser, newPattern)) {
            for (LectureParticipant lectureParticipant : lectureParticipants) {
                present.add(lectureParticipant.studentParticipant().user());
            }

            Lecture lec = new Lecture(teacher, newPattern);

            // update student schedule
            srv.schedule(present, newPattern);

            // update teacher schedule
            srv.schedule(sysUser, newPattern);

            this.lecture = lectureRepository.save(lec);

            return true;
        }

        return false;
    }

    private RecurringPattern onceBuildPattern(LocalDate newDate, LocalTime startTime, int durationMinutes) {
        RecurringPatternFreqOnceBuilder builder = new RecurringPatternFreqOnceBuilder();
        builder.withDate(newDate);
        builder.withDuration(startTime, durationMinutes);
        return builder.build();
    }

}
