package eapli.base.event.meeting.dto;

import eapli.base.event.meeting.domain.Meeting;
import jovami.util.dto.Mapper;

public class MeetingDTOMapper implements Mapper<Meeting, MeetingDTO> {

    @Override
    public MeetingDTO toDTO(Meeting meeting) {
        return new MeetingDTO(meeting);
    }
}
