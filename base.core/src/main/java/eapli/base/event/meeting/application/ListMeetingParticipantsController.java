package eapli.base.event.meeting.application;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.event.meeting.dto.MeetingOfUserDTO;
import eapli.base.event.meeting.dto.MeetingOfUserDTOMapper;
import eapli.base.event.meeting.dto.MeetingParticipantDTO;
import eapli.base.event.meeting.dto.MeetingParticipantDTOMapper;
import eapli.base.event.meeting.repositories.MeetingParticipantRepository;
import eapli.base.event.meeting.repositories.MeetingRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 * ListMeetingParticipantsController
 */
@UseCaseController
public final class ListMeetingParticipantsController {
    private final AuthorizationService authz;
    private final MeetingRepository meetingRepo;
    private final MeetingParticipantRepository participantRepo;

    private final ListMeetingsService meetingSvc;
    private final MyUserService userSvc;

    public ListMeetingParticipantsController() {
        this.authz = AuthzRegistry.authorizationService();
        var repos = PersistenceContext.repositories();

        this.meetingRepo = repos.meetings();
        this.participantRepo = repos.meetingParticipants();

        this.userSvc = new MyUserService();
        this.meetingSvc = new ListMeetingsService();
    }

    public Iterable<MeetingOfUserDTO> meetingsOfUser() {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.allRoles());
        var user = this.userSvc.currentUser();
        return new MeetingOfUserDTOMapper().toDTO(this.meetingSvc.meetingsOfUser(user));
    }

    public Iterable<MeetingParticipantDTO> meetingParticipants(MeetingOfUserDTO dto) {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.allRoles());

        var meeting = this.meetingRepo.ofIdentity(dto.meetingID())
                .orElseThrow(() -> new ConcurrencyException("Meeting somehow deleted"));
        return new MeetingParticipantDTOMapper().toDTO(this.participantRepo.meetingParticipants(meeting));
    }
}
