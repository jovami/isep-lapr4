package eapli.base.event.meeting.dto;

import eapli.base.event.meeting.domain.Description;
import eapli.base.event.meeting.domain.Meeting;
import eapli.framework.infrastructure.authz.domain.model.Username;

import java.time.LocalTime;
import java.util.Objects;

public class MeetingDTO {


    private final int meetingId;
    private final Username username;
    private final Description description;
    private final LocalTime time;
    private final int duration;


    public MeetingDTO(Meeting meeting) {
        meetingId= meeting.identity();
        username= meeting.admin().username();
        description= meeting.getDescription();
        time= meeting.pattern().startTime();
        duration= meeting.pattern().duration();
    }



    public int meetingId() {
        return meetingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingDTO that = (MeetingDTO) o;
        return meetingId == that.meetingId && duration == that.duration && Objects.equals(username, that.username) && Objects.equals(description, that.description) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, username, description, time, duration);
    }

    @Override
    public String toString() {
        return "Owner Username: " + username +
                ", with Subject: " + description.getDescription() +
                "\nStarting at: " + time +
                ", with Duration: " + duration + " minutes";
    }
}
