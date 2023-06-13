package eapli.base.event.lecture.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.TeacherBuilder;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

public class LectureTest {
    private Lecture lecture;
    private RecurringPattern pattern;
    private Teacher teacher;

    @Before
    public void setUp() {
        // Teacher
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        var user1 = userBuilder.with("alexandre", "Password1", "Alexandre", "Moreira", "alexmoreira@gmail.com")
                .withRoles(BaseRoles.TEACHER).build();
        final var teacherBuilder = new TeacherBuilder();
        teacherBuilder.withSystemUser(user1).withAcronym("LFF").withFullName("Alexandre Moreira").withShortName("Alex")
                .withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        teacher = teacherBuilder.build();
        // Pattern
        LocalDate startDate = LocalDate.of(2001, 1, 1);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;
        RecurringPatternFreqOnceBuilder builder = new RecurringPatternFreqOnceBuilder();
        builder.withDuration(startTime, duration);
        builder.withDate(startDate);
        pattern = builder.build();
        lecture = new Lecture(teacher, pattern);
    }

    @Test
    public void teacher() {
        Assertions.assertEquals(teacher, lecture.teacher());
    }

    @Test
    public void getPattern() {
        assertEquals(pattern, lecture.pattern());

    }

    @Test
    public void sameAsObject() {
        assertFalse(lecture.sameAs(new Object()));
    }

    @Test
    public void sameAsNull() {
        assertFalse(lecture.sameAs(null));
    }

    @Test
    public void sameAsSelf() {
        assertTrue(lecture.sameAs(lecture));
    }

    @Test
    public void sameAsSameId() {
        Lecture newLecture = new Lecture(teacher, pattern);
        assertTrue(lecture.sameAs(newLecture));
    }

    @Test
    public void testHashCode() {
        assertEquals(Objects.hash(0, teacher, pattern), lecture.hashCode());
    }

    @Test
    public void compareToBigger() {
        assertEquals(-1, lecture.compareTo(10));
    }

    @Test
    public void compareToEqual() {
        assertEquals(0, lecture.compareTo(0));
    }

    @Test
    public void compareToLower() {
        assertEquals(1, lecture.compareTo(-10));
    }

    @Test
    public void hasIdentity() {
        assertTrue(lecture.hasIdentity(0));
    }

    @Test
    public void hasIdentityFalse() {
        assertFalse(lecture.hasIdentity(10));
    }

    @Test
    public void testRegular() {
        lecture.regular();
        Assertions.assertEquals(LectureType.REGULAR, lecture.type());
    }

    @Test
    public void testExtra() {
        lecture.extra();
        Assertions.assertEquals(LectureType.EXTRA, lecture.type());
    }
}
