package eapli.base.event.meeting.application;

import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTO;
import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTOMapper;
import eapli.base.event.meeting.domain.Meeting;
import eapli.base.event.meeting.domain.MeetingParticipant;
import eapli.base.event.meeting.repositories.MeetingParticipantRepository;
import eapli.base.event.meeting.repositories.MeetingRepository;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.base.event.timetable.application.TimeTableService;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@UseCaseController
public class ScheduleMeetingController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final TransactionalContext txCtx = PersistenceContext.repositories()
            .newTransactionalContext();

    // Repositories
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final RecurringPatternRepository patternRepository;
    private final MeetingParticipantRepository participantRepository;
    // Service
    private final TimeTableService srv;
    // Domain
    private Meeting meeting;
    private ArrayList<SystemUser> invited = new ArrayList<>();
    private ArrayList<SystemUser> users;
    private RecurringPattern pattern;

    public ScheduleMeetingController() {
        meetingRepository = PersistenceContext.repositories().meetings();
        userRepository = PersistenceContext.repositories().users();
        patternRepository = PersistenceContext.repositories().recurringPatterns();
        participantRepository = PersistenceContext.repositories().meetingParticipants();
        users = (ArrayList<SystemUser>) userRepository.findAll();

        srv = new TimeTableService();
    }

    public boolean createMeeting(String description, LocalDate date, LocalTime startTime, int durationMinutes) {
        Optional<SystemUser> user = userRepository.ofIdentity(authz.session().get().authenticatedUser().identity());

        pattern = buildPattern(date, startTime, durationMinutes);
        pattern = patternRepository.save(pattern);

        if (pattern != null) {
            if (user.isPresent()) {
                meeting = new Meeting(user.get(), description, pattern);
                this.meeting = meetingRepository.save(meeting);
                if (schedule()){
                    return true;
                }
            }
        }
        return false;
    }

    private RecurringPattern buildPattern(LocalDate date, LocalTime startTime, int durationMinutes) {
        RecurringPatternFreqOnceBuilder builder = new RecurringPatternFreqOnceBuilder();
        builder.withDate(date);
        builder.withDuration(startTime, durationMinutes);
        return builder.build();
    }

    public boolean schedule() {
        if (srv.checkAvailability(invited, meeting.pattern())) {
            // create MeetingParticipant for each invited user

            for (SystemUser user : invited) {
                MeetingParticipant participant = new MeetingParticipant(user, meeting);
                participantRepository.save(participant);
            }

            if (srv.schedule(invited, meeting.pattern())) {
                return true;
            }

        }
        return false;
    }

    private SystemUser fromDTO(SystemUserNameEmailDTO dto) throws ConcurrencyException {
        return this.userRepository.ofIdentity(dto.username())
                .orElseThrow(() -> new ConcurrencyException("User no longer exists"));
    }

    public List<SystemUserNameEmailDTO> Users() {
        return new SystemUserNameEmailDTOMapper().toDTO(users, Comparator.comparing(SystemUser::identity));
    }

    public boolean invite(SystemUserNameEmailDTO dto) {
        SystemUser user = fromDTO(dto);
        if (invited.contains(user)) {
            return false;
        } else {
            invited.add(user);
            return true;
        }
    }
}
