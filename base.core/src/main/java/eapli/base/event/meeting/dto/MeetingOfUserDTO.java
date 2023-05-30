package eapli.base.event.meeting.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import eapli.base.event.meeting.domain.Description;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;

/**
 * MeetingOfUserDTO
 */
public final class MeetingOfUserDTO {

    private final int id;
    private final String description;
    private final LocalDateTime startTime;

    private final Name owner;
    private final EmailAddress ownerEmail;

    private static final DateTimeFormatter fmt;

    static {
        fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    public MeetingOfUserDTO(int id, Description description, LocalDateTime time, Name owner, EmailAddress email) {
        this.id = id;
        this.description = description.getDescription();
        this.startTime = time;
        this.owner = owner;
        this.ownerEmail = email;
    }

    public int meetingID() {
        return this.id;
    }

    public String description() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("%s, starting at %s (organized by: %s <%s>)",
                this.description,
                fmt.format(this.startTime),
                this.owner,
                this.ownerEmail);
    }
}
