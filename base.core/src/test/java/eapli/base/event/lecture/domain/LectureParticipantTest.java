package eapli.base.event.lecture.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.StudentBuilder;
import eapli.base.clientusermanagement.usermanagement.domain.TeacherBuilder;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqWeeklyBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

public class LectureParticipantTest {

    private LectureParticipant participant;
    private Lecture lecture;
    private Teacher teacher;
    private Student student;

    private TeacherBuilder teacherBuilder = new TeacherBuilder();
    private StudentBuilder studentBuilder = new StudentBuilder();

    @Before
    public void setUp() {

        // Teacher
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        var user1 = userBuilder.with("alexandre", "Password1", "Alexandre", "Moreira", "alexmoreira@gmail.com")
                .withRoles(BaseRoles.TEACHER).build();
        teacherBuilder = new TeacherBuilder();
        teacherBuilder.withSystemUser(user1).withAcronym("LFF").withFullName("Alexandre Moreira").withShortName("Alex")
                .withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        teacher = teacherBuilder.build();

        var user2 = userBuilder.with("antonio", "Password1", "Alexandre", "Moreira", "alexmoreira@gmail.com")
                .withRoles(BaseRoles.STUDENT).build();
        studentBuilder = new StudentBuilder();
        studentBuilder.withSystemUser(user2).withMecanographicNumber("isep568").withFullName("Miguel Novais")
                .withShortName("Miguel").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        student = studentBuilder.build();

        // Pattern
        LocalDate startDate = LocalDate.of(2001, 1, 1);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;
        RecurringPatternFreqOnceBuilder builder = new RecurringPatternFreqOnceBuilder();
        builder.withDuration(startTime, duration);
        builder.withDate(startDate);
        RecurringPattern pattern = builder.build();

        lecture = new Lecture(teacher, pattern);

        participant = new LectureParticipant(student, lecture);

    }

    @Test
    public void sameAsEqualValues() {
        LectureParticipant newParticipant = new LectureParticipant(student, lecture);
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
        // Student
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        var user2 = userBuilder.with("newUser", "duMMy1", "dummy", "dummy",
                "a@b.ro").withRoles(BaseRoles.STUDENT).build();
        studentBuilder = new StudentBuilder();
        studentBuilder.withSystemUser(user2).withMecanographicNumber("isep588").withFullName("Miguel Novais")
                .withShortName("Miguel").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        Student student2 = studentBuilder.build();
        // Pattern
        LocalDate startDate = LocalDate.of(2001, 1, 1);
        LocalTime startTime = LocalTime.of(10, 0);
        LocalDate endDate = LocalDate.of(2002, 1, 1);
        int duration = 120;
        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDayOfWeek(startDate.getDayOfWeek());
        builder.withDuration(startTime, duration);
        builder.withDateInterval(startDate, endDate);
        RecurringPattern pattern = builder.build();

        Lecture newlecture = new Lecture(teacher, pattern);
        LectureParticipant newParticipant = new LectureParticipant(student2, lecture);

        Assertions.assertFalse(participant.sameAs(newParticipant));
    }

    @Test
    public void sameAsDiffLecture() {
        // Student
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        var user2 = userBuilder.with("newUser", "duMMy1", "dummy", "dummy",
                "a@b.ro").withRoles(BaseRoles.STUDENT).build();
        studentBuilder = new StudentBuilder();
        studentBuilder.withSystemUser(user2).withMecanographicNumber("isep568").withFullName("Miguel Novais")
                .withShortName("Miguel").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        Student student2 = studentBuilder.build();
        // Pattern
        LocalDate startDate = LocalDate.of(2004, 1, 1);
        LocalTime startTime = LocalTime.of(10, 0);
        LocalDate endDate = LocalDate.of(2008, 1, 1);
        int duration = 120;
        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDayOfWeek(startDate.getDayOfWeek());
        builder.withDuration(startTime, duration);
        builder.withDateInterval(startDate, endDate);
        RecurringPattern pattern = builder.build();

        Lecture newlecture = new Lecture(teacher, pattern);
        LectureParticipant newParticipant = new LectureParticipant(student2, newlecture);

        Assertions.assertFalse(participant.sameAs(newParticipant));
    }

    @Test
    public void lecture() {
        Assertions.assertEquals(lecture, participant.lecture());
    }
}
