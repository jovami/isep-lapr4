package eapli.base.event.lecture.application;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.domain.LectureParticipant;
import eapli.base.event.lecture.repositories.LectureParticipantRepository;
import eapli.base.event.timetable.application.TimeTableService;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.Collection;

public class ScheduleLectureService {

    private final LectureParticipantRepository participantRepo;
    private final StudentRepository studentRepo;

    private final TimeTableService timeTableService;

    public ScheduleLectureService() {
        this.participantRepo = PersistenceContext.repositories().lectureParticipants();
        this.studentRepo = PersistenceContext.repositories().students();
        this.timeTableService = new TimeTableService();
    }

    public boolean scheduleLecture(SystemUser organizer, Collection<SystemUser> invited, Lecture lecture) {
        final var pattern = lecture.pattern();
        if (!this.timeTableService.checkAvailabilityByUser(organizer, pattern))
            return false;

        this.timeTableService.schedule(organizer, pattern);
        for (SystemUser user : invited) {
            this.timeTableService.schedule(user, pattern);
            LectureParticipant participant = new LectureParticipant(findStudentBySystemUser(user), lecture);
            participantRepo.save(participant);
        }

        return true;
    }

    private Student findStudentBySystemUser(SystemUser user) {
        return studentRepo.findByUsername(user.username()).orElseThrow();
    }
}
