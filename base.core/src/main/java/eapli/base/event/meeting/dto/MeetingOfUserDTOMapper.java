package eapli.base.event.meeting.dto;

import java.time.LocalDateTime;

import eapli.base.event.meeting.domain.Meeting;
import jovami.util.dto.Mapper;

/**
 * MeetingOfUserDTOMapper
 */
public final class MeetingOfUserDTOMapper implements Mapper<Meeting, MeetingOfUserDTO> {

    @Override
    public MeetingOfUserDTO toDTO(Meeting meeting) {
        var date = meeting.pattern().startDate();
        var time = meeting.pattern().startTime();

        var dateTime = LocalDateTime.of(date, time);

        var admin = meeting.admin();

        return new MeetingOfUserDTO(meeting.identity(), meeting.getDescription(), dateTime, admin.name(), admin.email());
    }

}
