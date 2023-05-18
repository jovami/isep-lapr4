package eapli.base.event.Meeting.domain;

import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static org.junit.Assert.*;

class MeetingTest {
    private Meeting meeting;
    private SystemUser user;
    private RecurringPattern pattern;

    @BeforeEach
    void setUp() {
        //SystemUser
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        userBuilder.with("user", "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(BaseRoles.MANAGER);

        //Pattern
        LocalDate startDate = LocalDate.of(2001,1,1);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;
        RecurringPatternFreqOnceBuilder builder = new RecurringPatternFreqOnceBuilder();
        builder.withDuration(startTime,duration);
        builder.withDate(startDate);
        user = userBuilder.build();
        pattern = builder.build();
        meeting = new Meeting(user, "description",pattern);
    }

    @Test
    void admin() {
        Assertions.assertEquals(user,meeting.admin());
    }

    @Test
    void setMeetingAdmin() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        userBuilder.with("other", "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(BaseRoles.MANAGER);
        SystemUser newAdmin = userBuilder.build();
        meeting.chooseMeetingAdmin(newAdmin);
        Assertions.assertEquals(newAdmin,meeting.admin());
    }

    @Test
    void testHandleDescription() {
        meeting.setDescription(new Description("new description"));
        Description description = new Description("new description");
        assertEquals(description,meeting.getDescription());
    }

    @Test
    void getPattern() {
        assertEquals(pattern,meeting.pattern());

    }

    @Test
    void sameAsObject() {
        assertFalse(meeting.sameAs(new Object()));
    }
    @Test
    void sameAsNull() {
        assertFalse(meeting.sameAs(null));
    }
    @Test
    void sameAsSelf() {
        assertTrue(meeting.sameAs(meeting));
    }
    @Test
    void sameAsSameId() {
        Meeting newMeeting= new Meeting(user, "description",pattern);
        assertTrue(meeting.sameAs(newMeeting));
    }

    @Test
    void testHashCode() {
        assertEquals(Objects.hash(0,user,pattern),meeting.hashCode());
    }

    @Test
    void compareToBigger() {
        assertEquals(-1,meeting.compareTo(10));
    }


    @Test
    void compareToEqual() {
        assertEquals(0,meeting.compareTo(0));
    }

    @Test
    void compareToLower() {
        assertEquals(1,meeting.compareTo(-10));
    }

    @Test
    void hasIdentity() {
        assertTrue(meeting.hasIdentity(0));
    }
    @Test
    void hasIdentityFalse() {
        assertFalse(meeting.hasIdentity(10));
    }

}