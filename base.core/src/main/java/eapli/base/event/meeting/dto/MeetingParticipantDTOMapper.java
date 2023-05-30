package eapli.base.event.meeting.dto;

import eapli.base.event.meeting.domain.MeetingParticipant;
import jovami.util.dto.Mapper;

/**
 * MeetingParticipantDTOMapper
 */
public final class MeetingParticipantDTOMapper implements Mapper<MeetingParticipant, MeetingParticipantDTO> {

    @Override
    public MeetingParticipantDTO toDTO(MeetingParticipant participant) {
        var user = participant.participant();
        return new MeetingParticipantDTO(user.username(), user.email(), participant.status());
    }

}
