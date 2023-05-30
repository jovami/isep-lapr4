package eapli.base.event.meeting.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

public class MeetingParticipantTest {

    private MeetingParticipant participant;
    private Meeting meeting;
    private SystemUser user;

    @Before
    public void setUp() {

        // SystemUser
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        userBuilder.with("user", "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(BaseRoles.MANAGER);
        // Pattern
        LocalDate startDate = LocalDate.of(2001, 1, 1);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;
        RecurringPatternFreqOnceBuilder builder = new RecurringPatternFreqOnceBuilder();
        builder.withDuration(startTime, duration);
        builder.withDate(startDate);
        RecurringPattern pattern = builder.build();

        user = userBuilder.build();

        meeting = new Meeting(user, "description", pattern);
        participant = new MeetingParticipant(user, meeting);

    }

    @Test
    public void sameAsEqualValues() {
        MeetingParticipant newParticipant = new MeetingParticipant(user, meeting);
        Assertions.assertTrue(participant.sameAs(newParticipant));
    }

    @Test
    public void sameAsSameObject() {
        Assertions.assertTrue(participant.sameAs(participant));
    }

    @Test
    public void sameAsNull() {
        Assertions.assertFalse(participant.sameAs(null));
    }

    @Test
    public void sameAsOtherObject() {
        Assertions.assertFalse(participant.sameAs(new Object()));
    }

    @Test
    public void compareTo() {
        Assertions.assertEquals(0, participant.compareTo(0));
    }

    @Test
    public void compareToBigger() {
        Assertions.assertEquals(-1, participant.compareTo(10));
    }

    @Test
    public void compareToLower() {
        Assertions.assertEquals(1, participant.compareTo(-10));
    }

    @Test
    public void identity() {
        Assertions.assertEquals(0, participant.identity());
    }

    @Test
    public void hasIdentityFalse() {
        Assertions.assertFalse(participant.hasIdentity(-10));
    }

    @Test
    public void hasIdentityTrue() {
        Assertions.assertTrue(participant.hasIdentity(0));
    }

    @Test
    public void sameAsDiffUser() {
        // SystemUser
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        userBuilder.with("newUser", "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(BaseRoles.MANAGER);
        // Pattern
        LocalDate startDate = LocalDate.of(2001, 1, 1);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;
        RecurringPatternFreqOnceBuilder builder = new RecurringPatternFreqOnceBuilder();
        builder.withDuration(startTime, duration);
        builder.withDate(startDate);
        RecurringPattern pattern = builder.build();

        SystemUser newUser = userBuilder.build();

        Meeting newMeeting = new Meeting(user, "description", pattern);
        MeetingParticipant newParticipant = new MeetingParticipant(newUser, meeting);

        Assertions.assertFalse(participant.sameAs(newParticipant));
    }

    @Test
    public void sameAsDiffMeeting() {
        // SystemUser
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        userBuilder.with("newUser", "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(BaseRoles.MANAGER);
        // Pattern
        LocalDate startDate = LocalDate.of(2003, 1, 1);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;
        RecurringPatternFreqOnceBuilder builder = new RecurringPatternFreqOnceBuilder();
        builder.withDuration(startTime, duration);
        builder.withDate(startDate);
        RecurringPattern pattern = builder.build();

        SystemUser newUser = userBuilder.build();

        Meeting newMeeting = new Meeting(user, "description", pattern);
        MeetingParticipant newParticipant = new MeetingParticipant(user, newMeeting);

        Assertions.assertFalse(participant.sameAs(newParticipant));
    }

    @Test
    public void testAccept() {
        participant.accept();
        Assertions.assertEquals(MeetingParticipantStatus.ACCEPTED, participant.status());
    }

    @Test
    public void testReject() {
        participant.reject();
        Assertions.assertEquals(MeetingParticipantStatus.REJECTED, participant.status());
    }

    @Test
    public void testPending() {
        Assertions.assertEquals(MeetingParticipantStatus.PENDING, participant.status());
    }

    @Test
    public void meeting() {
        Assertions.assertEquals(meeting, participant.meeting());
    }
}
