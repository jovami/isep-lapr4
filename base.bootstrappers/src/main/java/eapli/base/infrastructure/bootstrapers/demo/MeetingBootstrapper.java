package eapli.base.infrastructure.bootstrapers.demo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import eapli.base.clientusermanagement.domain.users.Manager;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.ManagerRepository;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.event.meeting.domain.Description;
import eapli.base.event.meeting.domain.Meeting;
import eapli.base.event.meeting.domain.MeetingParticipant;
import eapli.base.event.meeting.repositories.MeetingParticipantRepository;
import eapli.base.event.meeting.repositories.MeetingRepository;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.base.event.timetable.application.TimeTableService;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;

public class MeetingBootstrapper implements Action {

    private final StudentRepository studentRepository = PersistenceContext.repositories().students();
    private final ManagerRepository managerRepository = PersistenceContext.repositories().managers();
    private final TeacherRepository teacherRepository = PersistenceContext.repositories().teachers();
    private final MeetingRepository meetingRepository = PersistenceContext.repositories().meetings();
    private RecurringPattern recurringPattern = new RecurringPattern();
    private final RecurringPatternRepository recurringPatternRepository = PersistenceContext.repositories()
            .recurringPatterns();
    private final MeetingParticipantRepository meetingParticipantRepository = PersistenceContext.repositories()
            .meetingParticipants();

    private final RecurringPatternFreqOnceBuilder recurringPatternFreqOnceBuilder = new RecurringPatternFreqOnceBuilder();

    private final TimeTableService timeTableService = new TimeTableService();

    @Override
    public boolean execute() {

        registerMeeting(Username.valueOf("johnny"), Username.valueOf("mary"), Description.valueOf("Study Fisics"),
                LocalDate.of(2030, 1, 1), LocalTime.parse("10:00"), 120);
        registerMeeting(Username.valueOf("johnny"), Username.valueOf("ruben"), Description.valueOf("Study Tugas"),
                LocalDate.of(2027, 1, 1), LocalTime.parse("10:00"), 120);
        registerMeeting(Username.valueOf("johnny"), Username.valueOf("marco1"), Description.valueOf("Study Math"),
                LocalDate.of(2025, 1, 1), LocalTime.parse("10:00"), 120);
        registerMeeting(Username.valueOf("johnny"), Username.valueOf("marco1"), Description.valueOf("Study Computers"),
                LocalDate.of(2027, 1, 1), LocalTime.parse("12:00"), 10);
        registerMeeting(Username.valueOf("johnny"), Username.valueOf("marco1"),
                Description.valueOf("Prepare presentation"),
                LocalDate.of(2027, 3, 6), LocalTime.parse("12:00"), 40);

        return true;
    }

    private void registerMeeting(Username participant, Username owner, Description description,
            LocalDate date, LocalTime time, int duration) {

        SystemUser userParticipant = meetingParticipant(participant);

        SystemUser userOwner = meetingParticipant(owner);

        recurringPattern = recurringPatternFreqOnceBuilder.withDate(date).withDuration(time, duration).build();
        recurringPattern = recurringPatternRepository.save(recurringPattern);

        Meeting meeting = new Meeting(userOwner, description.getDescription(), recurringPattern);
        meeting = meetingRepository.save(meeting);

        meetingParticipantRepository.save(new MeetingParticipant(userParticipant, meeting));

        timeTableService.schedule(userOwner, recurringPattern);

    }

    private SystemUser meetingParticipant(Username participant) {

        Optional<Student> studentParticipant = studentRepository.findByUsername(participant);
        if (studentParticipant.isPresent())
            return studentParticipant.get().user();

        Optional<Teacher> teacherParticipant = teacherRepository.findByUser(participant);
        if (teacherParticipant.isPresent())
            return teacherParticipant.get().user();

        Optional<Manager> managerParticipant = managerRepository.findByUsername(participant);
        return managerParticipant.map(Manager::user).orElse(null);

    }

}
