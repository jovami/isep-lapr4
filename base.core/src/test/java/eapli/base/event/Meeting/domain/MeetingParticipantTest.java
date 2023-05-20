package eapli.base.event.Meeting.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

class MeetingParticipantTest {

    private MeetingParticipant participant;
    private Meeting meeting;
    private SystemUser user;

    @BeforeEach
    void setUp() {

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
    void sameAsEqualValues() {
        MeetingParticipant newParticipant = new MeetingParticipant(user, meeting);
        Assertions.assertTrue(participant.sameAs(newParticipant));
    }

    @Test
    void sameAsSameObject() {
        Assertions.assertTrue(participant.sameAs(participant));
    }

    @Test
    void sameAsNull() {
        Assertions.assertFalse(participant.sameAs(null));
    }

    @Test
    void sameAsOtherObject() {
        Assertions.assertFalse(participant.sameAs(new Object()));
    }

    @Test
    void compareTo() {
        Assertions.assertEquals(0, participant.compareTo(0));
    }

    @Test
    void compareToBigger() {
        Assertions.assertEquals(-1, participant.compareTo(10));
    }

    @Test
    void compareToLower() {
        Assertions.assertEquals(1, participant.compareTo(-10));
    }

    @Test
    void identity() {
        Assertions.assertEquals(0, participant.identity());
    }

    @Test
    void hasIdentityFalse() {
        Assertions.assertFalse(participant.hasIdentity(-10));
    }

    @Test
    void hasIdentityTrue() {
        Assertions.assertTrue(participant.hasIdentity(0));
    }

    @Test
    void sameAsDiffUser() {
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
    void sameAsDiffMeeting() {
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
    void testAccept() {
        participant.accept();
        Assertions.assertEquals(MeetingParticipantStatus.ACCEPTED, participant.status());
    }

    @Test
    void testDeny() {
        participant.deny();
        Assertions.assertEquals(MeetingParticipantStatus.REJECTED, participant.status());
    }

    @Test
    void testPending() {
        participant.deny();
        Assertions.assertEquals(MeetingParticipantStatus.REJECTED, participant.status());
    }

    @Test
    void meeting() {
        Assertions.assertEquals(meeting, participant.meeting());
    }
}
