package eapli.base.exam.domain;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.StudentBuilder;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseFactory;
import eapli.base.course.dto.CreateCourseDTO;
import eapli.base.examresult.domain.ExamGrade;
import eapli.base.examresult.domain.ExamGradeProperties;
import eapli.base.examresult.domain.RegularExamResult;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class RegularExamResultTest {
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;
    private RegularExam regularExam1;
    private RegularExam regularExam2;

    @Before
    public void setUp() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        final var df = DateTimeFormatter.ofPattern("d/M/yyyy");

        var sDate = LocalDate.parse("20/05/2020", df);
        var eDate = LocalDate.parse("20/09/2020", df);

        var dto1 = new CreateCourseDTO("PYTHON", 1L, "Python for beginners :)", sDate, eDate, 10, 24);
        var dto2 = new CreateCourseDTO("JAVA", 3L, "Java advanced!", sDate, eDate, 20, 34);

        course1 = new CourseFactory().build(dto1);
        course2 = new CourseFactory().build(dto2);

        var user1 = userBuilder.with("alexandre", "Password1", "Alexandre", "Moreira", "alexmoreira@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        var user2 = userBuilder.with("miguel", "Password1", "Miguel", "Novais", "mnovais672@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();

        final var studentBuilder = new StudentBuilder();
        studentBuilder.withSystemUser(user1)
                .withMecanographicNumber("isep567")
                .withFullName("Alexandre Moreira")
                .withShortName("Alex")
                .withDateOfBirth("2001-01-01")
                .withTaxPayerNumber("123756789");
        student1 = studentBuilder.build();

        studentBuilder.withSystemUser(user2)
                .withMecanographicNumber("isep568")
                .withFullName("Miguel Novais")
                .withShortName("Miguel")
                .withDateOfBirth("2001-01-01")
                .withTaxPayerNumber("123756789");
        student2 = studentBuilder.build();

        final var dfExam = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");
        var openDate = LocalDateTime.parse("10/10/2023 16:00", dfExam);
        var closeDate = LocalDateTime.parse("10/10/2023 18:00", dfExam);

        regularExam1 = new RegularExam(RegularExamTitle.valueOf("test1")
                ,new RegularExamSpecification("test specification")
                , RegularExamDate.valueOf(openDate, closeDate)
                , course1);

        regularExam2 = new RegularExam(RegularExamTitle.valueOf("test2")
                , new RegularExamSpecification("test specification")
                , RegularExamDate.valueOf(openDate, closeDate)
                , course2);
    }

    @Test
    public void ensureCreationOfExamResultWithValidParametersWorks() {
        {
            var examResult = new RegularExamResult(student1, regularExam1
                    , ExamGrade.valueOf(10.f, 20.f)
                    , ExamGradeProperties.NONE);
            assertEquals(examResult.student(), student1);
            assertEquals(examResult.regularExam(), regularExam1);
            assertEquals(examResult.grade(), ExamGrade.valueOf(10.f, 20.f));
            assertEquals(examResult.gradeProperties(), ExamGradeProperties.NONE);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCreateExamResultWithNullParametersThrowsException() {
        new RegularExamResult(null, (RegularExam) null, null, null);
    }

    @Test
    public void ensureSameAsWorksAsExpected() {
        var examResult1 = new RegularExamResult(student1, regularExam1
                , ExamGrade.valueOf(10.F, 20.f)
                , ExamGradeProperties.NONE);
        var examResult2 = new RegularExamResult(student1, regularExam1
                , ExamGrade.valueOf(10F, 20.f)
                , ExamGradeProperties.NONE);

        var examResult3 = new RegularExamResult(student2, regularExam2
                , ExamGrade.valueOf(12F, 15.5f)
                , ExamGradeProperties.ON_SUBMISSION);

        assertTrue(examResult1.sameAs(examResult1));
        assertTrue(examResult1.sameAs(examResult2));
        assertNotEquals(examResult1, examResult3);
    }

    @Test
    public void ensureSameAsReturnsFalseIfComparedWithNull() {
        var examResult1 = new RegularExamResult(student1, regularExam1
                , ExamGrade.valueOf(10.f, 20.f)
                , ExamGradeProperties.NONE);
        assertNotEquals(examResult1, null);
    }

    @Test
    public void ensureSameAsReturnsFalseIfComparedWithAnotherClass() {
        var examResult1 = new RegularExamResult(student1, regularExam1
                , ExamGrade.valueOf(10F, 20.f)
                , ExamGradeProperties.NONE);
        assertNotEquals(examResult1, "I'm not a RegularExamResult :/, I'm a string");
    }
}
