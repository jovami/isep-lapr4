package eapli.base.event.meeting.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

public class MeetingTest {
    private Meeting meeting;
    private SystemUser user;
    private RecurringPattern pattern;

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
        user = userBuilder.build();
        pattern = builder.build();
        meeting = new Meeting(user, "description", pattern);
    }

    @Test
    public void admin() {
        Assertions.assertEquals(user, meeting.admin());
    }

    @Test
    public void setMeetingAdmin() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        userBuilder.with("other", "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(BaseRoles.MANAGER);
        SystemUser newAdmin = userBuilder.build();
        meeting.chooseMeetingAdmin(newAdmin);
        Assertions.assertEquals(newAdmin, meeting.admin());
    }

    @Test
    public void testHandleDescription() {
        meeting.setDescription(new Description("new description"));
        Description description = new Description("new description");
        assertEquals(description, meeting.getDescription());
    }

    @Test
    public void getPattern() {
        assertEquals(pattern, meeting.pattern());
    }

    @Test
    public void sameAsObject() {
        assertFalse(meeting.sameAs(new Object()));
    }

    @Test
    public void sameAsNull() {
        assertFalse(meeting.sameAs(null));
    }

    @Test
    public void sameAsSelf() {
        assertTrue(meeting.sameAs(meeting));
    }

    @Test
    public void sameAsSameId() {
        Meeting newMeeting = new Meeting(user, "description", pattern);
        assertTrue(meeting.sameAs(newMeeting));
    }

    @Test
    public void testHashCode() {
        assertEquals(Objects.hash(0, user, pattern), meeting.hashCode());
    }

    @Test
    public void compareToBigger() {
        assertEquals(-1, meeting.compareTo(10));
    }

    @Test
    public void compareToEqual() {
        assertEquals(0, meeting.compareTo(0));
    }

    @Test
    public void compareToLower() {
        assertEquals(1, meeting.compareTo(-10));
    }

    @Test
    public void hasIdentity() {
        assertTrue(meeting.hasIdentity(0));
    }

    @Test
    public void hasIdentityFalse() {
        assertFalse(meeting.hasIdentity(10));
    }
}
