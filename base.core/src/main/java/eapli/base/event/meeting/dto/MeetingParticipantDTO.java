package eapli.base.event.meeting.dto;

import eapli.base.event.meeting.domain.MeetingParticipantStatus;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * MeetingParticipantDTO
 */
public final class MeetingParticipantDTO {

    private final Username name;
    private final EmailAddress email;
    private final MeetingParticipantStatus status;


    public MeetingParticipantDTO(Username name, EmailAddress email, MeetingParticipantStatus status) {
        this.name = name;
        this.email = email;
        this.status = status;
    }


    @Override
    public String toString() {
        return String.format("%s <%s>, with status: %s", this.name, this.email, this.status);
    }
}
