package eapli.base.event.meeting.application;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.event.meeting.domain.Meeting;
import eapli.base.event.meeting.domain.MeetingState;
import eapli.base.event.meeting.dto.MeetingDTO;
import eapli.base.event.meeting.dto.MeetingDTOMapper;
import eapli.base.event.meeting.repositories.MeetingRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.ConcurrencyException;

import java.util.List;

public class CancelMeetingController {
    private final MeetingRepository meetingRepo = PersistenceContext.repositories().meetings();
    private final MeetingService meetingService;
    private final MyUserService userService = new MyUserService();

    public CancelMeetingController() {
        meetingService = new MeetingService();
    }

    public List<MeetingDTO> meetings() {
        return new MeetingDTOMapper().toDTO(
                this.meetingRepo.meetingsOfAdminWithState(userService.currentUser(), MeetingState.SCHEDULED));
    }

    public boolean cancel(MeetingDTO dto) {
        return meetingService.cancelMeeting(fromDTO(dto));
    }

    private Meeting fromDTO(MeetingDTO dto) throws ConcurrencyException {
        return this.meetingRepo.ofIdentity(dto.meetingId()).orElseThrow(() -> new ConcurrencyException("User no longer exists"));
    }

}
