package eapli.base.event.meeting.application;

import java.util.Optional;

import eapli.base.event.meeting.domain.Meeting;
import eapli.base.event.meeting.domain.MeetingParticipant;
import eapli.base.event.meeting.dto.MeetingDTO;
import eapli.base.event.meeting.dto.MeetingDTOMapper;
import eapli.base.event.meeting.repositories.MeetingParticipantRepository;
import eapli.base.event.meeting.repositories.MeetingRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public class AcceptRejectMeetingRequestController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final MeetingParticipantRepository participantRepository = PersistenceContext.repositories()
            .meetingParticipants();
    private final MeetingRepository meetingRepository = PersistenceContext.repositories().meetings();
    private final AcceptRejectMeetingRequestService service = new AcceptRejectMeetingRequestService();

    public AcceptRejectMeetingRequestController() {
    }

    public Iterable<MeetingDTO> showInvitedMeetings() {
        return new MeetingDTOMapper().toDTO(invitedMeetings());
    }

    public Iterable<Meeting> invitedMeetings() {
        return meetingRepository
                .findAllMeetingsWithParticipantWithPendingStatus(authz.session().get().authenticatedUser());
    }

    public boolean acceptMeetingRequest(MeetingDTO part) {
        if (authz.session().isEmpty())
            return false;
        Optional<MeetingParticipant> participant = participantRepository
                .findMeetingParticipantByUserAndMeeting(authz.session().get().authenticatedUser(), fromDTO(part));
        return participant.filter(service::acceptMeetingRequest).isPresent();
    }

    public boolean rejectMeetingRequest(MeetingDTO part) {
        if (authz.session().isEmpty())
            return false;
        Optional<MeetingParticipant> participant = participantRepository
                .findMeetingParticipantByUserAndMeeting(authz.session().get().authenticatedUser(), fromDTO(part));
        return participant.filter(service::rejectMeetingRequest).isPresent();
    }

    private Meeting fromDTO(MeetingDTO dto) throws ConcurrencyException {
        return this.meetingRepository.ofIdentity(dto.meetingId())
                .orElseThrow(() -> new ConcurrencyException("User no longer exists"));
    }

}
