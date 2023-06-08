package jovami.grammar.impl.antlr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseFactory;
import eapli.base.course.dto.CreateCourseDTO;
import eapli.base.question.domain.QuestionFactory;

/**
 * FormativeExamFactoryTest
 */
public class QuestionFactoryTest {
    private Course FISICA = null;

    private QuestionFactory factory;

    @Before
    public void makeFactory() {
        this.factory = new QuestionFactory(new ANTLRQuestionValidator());
    }

    @Before
    public void buildCourse() {
        final var df = DateTimeFormatter.ofPattern("d/M/yyyy");

        final var name = "Fisica";
        final var description = "Fisica dos materiais";
        final var minStudents = 10;
        final var maxStudents = 100;

        final var startDate = LocalDate.parse("20/05/2020", df);
        final var endDate = LocalDate.parse("20/09/2020", df);

        final var dto = new CreateCourseDTO(name, 1L, description, startDate, endDate, minStudents, maxStudents);
        final var course = new CourseFactory().build(dto);
        course.open();

        FISICA = course;
    }

    @Test
    public void ensureMatchingMustHaveSolution() {
        // @formatter:off
        var spec = List.of("MATCHING {",
                "DESCRIPTION: \"Match the countries with their capital cities\"",

                "SUBQUESTION 1: \"Portugal\"",
                "SUBQUESTION 2: \"Spain\"",
                "SUBQUESTION 3: \"France\"",
                "SUBQUESTION 4: \"Italy\"",

                "ANSWER 1: \"Lisbon\"",
                "ANSWER 2: \"Madrid\"",
                "ANSWER 3: \"Paris\"",
                "ANSWER 4: \"Rome\"",
                "}");
        // @formatter:on

        var question = this.factory.build(FISICA, spec);
        assertTrue(question.isEmpty(), "Solution not found");

        // @formatter:off
        spec = List.of("MATCHING {",
                "DESCRIPTION: \"Match the countries with their capital cities\"",

                "SUBQUESTION 1: \"Portugal\"",
                "SUBQUESTION 2: \"Spain\"",
                "SUBQUESTION 3: \"France\"",
                "SUBQUESTION 4: \"Italy\"",

                "ANSWER 1: \"Lisbon\"",
                "ANSWER 2: \"Madrid\"",
                "ANSWER 3: \"Paris\"",
                "ANSWER 4: \"Rome\"",

                "SOLUTION 1: 1-2 [1.0]",
                "SOLUTION 2: 2-1 [1.0]",
                "SOLUTION 3: 3-3 [1.0]",
                "SOLUTION 4: 4-4 [1.0]",
                "}");
        // @formatter:on

        question = this.factory.build(FISICA, spec);
        assertTrue(question.isPresent(), "Matching Question had everything but still failed");
    }

    @Test
    public void ensureMultipleChoiceMustHaveDescription() {
        // @formatter:off
        var spec = List.of("MULTIPLE_CHOICE {",
                "CHOICE_TYPE: single-answer",

                "ANSWER 1: \"Sally Ride\"",
                "ANSWER 2: \"Valentina Tereshkova\"",
                "ANSWER 3: \"Mae Jemison\"",
                "ANSWER 4: \"Yuri Gagarin\"",

                "SOLUTION 1: 2 [5.0]",
                "}");
        // @formatter:on

        var fexam = this.factory.build(FISICA, spec);
        assertTrue(fexam.isEmpty(), "Description not found");

        // @formatter:off
        spec = List.of("MULTIPLE_CHOICE {",
                "CHOICE_TYPE: single-answer",
                "DESCRIPTION: \"Who was the first female astronaut to travel to space?\"",

                "ANSWER 1: \"Sally Ride\"",
                "ANSWER 2: \"Valentina Tereshkova\"",
                "ANSWER 3: \"Mae Jemison\"",
                "ANSWER 4: \"Yuri Gagarin\"",

                "SOLUTION 1: 2 [5.0]",
                "}");
        // @formatter:on

        fexam = this.factory.build(FISICA, spec);
        assertTrue(fexam.isPresent(), "Multiple Choice Question had everything but still failed");
    }

    @Test
    public void ensureMultipleChoiceMustHaveAnswer() {
        // @formatter:off
        var spec = List.of("MULTIPLE_CHOICE {",
                "CHOICE_TYPE: multiple-answer",
                "DESCRIPTION: \"Which of the following animals are mammals?\"",

                "SOLUTION 1: 1,3 [2.0]",
                "SOLUTION 2: 1,3,4 [1.5]",
                "SOLUTION 3: 1,4 [1.0]",
                "SOLUTION 4: 3,4 [0.5]",
                "SOLUTION 5: 1 [0.2]",
                "SOLUTION 6: 3 [0.2]",
                "SOLUTION 7: 4 [0.1]",
                "}");
        // @formatter:on

        var fexam = this.factory.build(FISICA, spec);
        assertTrue(fexam.isEmpty(), "Answer not found");

        // @formatter:off
        spec = List.of("MULTIPLE_CHOICE {",
                "CHOICE_TYPE: multiple-answer",
                "DESCRIPTION: \"Which of the following animals are mammals?\"",

                "ANSWER 1: \"Kangaroo\"",
                "ANSWER 2: \"Turtle\"",
                "ANSWER 3: \"Dolphin\"",
                "ANSWER 4: \"Salmon\"",

                "SOLUTION 1: 1,3 [2.0]",
                "SOLUTION 2: 1,3,4 [1.5]",
                "SOLUTION 3: 1,4 [1.0]",
                "SOLUTION 4: 3,4 [0.5]",
                "SOLUTION 5: 1 [0.2]",
                "SOLUTION 6: 3 [0.2]",
                "SOLUTION 7: 4 [0.1]",
                "}");
        // @formatter:on

        fexam = this.factory.build(FISICA, spec);
        assertTrue(fexam.isPresent(), "Multiple Choice Question had everything but still failed");
    }

    @Test
    public void ensureShortAnswerMustSpecifyCaseSensitivity() {
        // @formatter:off
        var spec = List.of("SHORT_ANSWER {",
                "DESCRIPTION: \"Where is Casa da Música located?\"",

                "SOLUTION 1: \"Boavista\" [0.5]",
                "SOLUTION 2: \"Avenida da Boavista\" [1.0]",
                "}");
        // @formatter:on

        var fexam = this.factory.build(FISICA, spec);
        assertTrue(fexam.isEmpty(), "Case sensitive not found");

        // @formatter:off
        spec = List.of("SHORT_ANSWER {",
                "DESCRIPTION: \"Where is Casa da Música located?\"",
                "CASE_SENSITIVE: false",

                "SOLUTION 1: \"Boavista\" [0.5]",
                "SOLUTION 2: \"Avenida da Boavista\" [1.0]",
                "}");
        // @formatter:on

        fexam = this.factory.build(FISICA, spec);
        assertTrue(fexam.isPresent(), "Short Answer Question had everything but still failed");
    }

    @Test
    public void ensureNumericalMustHaveSolution() {
        // @formatter:off
        var spec = List.of("NUMERICAL {",
                "DESCRIPTION: \"What is the value of 1+1?\"",
                "ERROR: 0.5",
                "}");
        // @formatter:on

        var fexam = this.factory.build(FISICA, spec);
        assertTrue(fexam.isEmpty(), "Solution not found");

        // @formatter:off
        spec = List.of("NUMERICAL {",
                "DESCRIPTION: \"What is the value of 1+1?\"",
                "ERROR: 0.5",

                "SOLUTION 1: 2 [1.0]",
                "}");
        // @formatter:on

        fexam = this.factory.build(FISICA, spec);
        assertTrue(fexam.isPresent(), "Numerical Question had everything but still failed");
    }

    @Test
    public void ensureMissingWordsMustSpecifyGroup() {
        // @formatter:off
        var spec = List.of("MISSING_WORDS {",
                "DESCRIPTION: \"The Wright brothers were the first to successfully [1] an airplane.\"",

                "CHOICE 1 {",
                " SOLUTION 1: 1 [1.5]",
                " FROM_GROUP: \"verbs\"",
                "}",
                "}");
        // @formatter:on

        var fexam = this.factory.build(FISICA, spec);
        assertTrue(fexam.isEmpty(), "Group verbs not found");

        // @formatter:off
        spec = List.of("MISSING_WORDS {",
                "DESCRIPTION: \"The Wright brothers were the first to successfully [1] an airplane.\"",

                "GROUP \"verbs\" {",
                "ITEM 1: \"fly\"",
                "ITEM 2: \"drive\"",
                "ITEM 3: \"swim\"",
                "}",

                "CHOICE 1 {",
                " SOLUTION 1: \"fly\" [1.5]",
                " FROM_GROUP: \"verbs\"",
                "}",
                "}");
        // @formatter:on

        fexam = this.factory.build(FISICA, spec);
        assertTrue(fexam.isPresent(), "Missing words Question had everything but still failed");
    }

    @Test
    public void ensureTrueFalseMustHaveSolution() {
        // @formatter:off
        var spec = List.of("TRUE_FALSE {",
                "DESCRIPTION: \"Water boils at 100 degrees Celsius.\"",
                "}");
        // @formatter:on

        var fexam = this.factory.build(FISICA, spec);
        assertTrue(fexam.isEmpty(), "Solution not found");

        // @formatter:off
        spec = List.of("TRUE_FALSE {",
                "DESCRIPTION: \"Water boils at 100 degrees Celsius.\"",
                "SOLUTION 1: true [1.0]",
                "}");
        // @formatter:on

        fexam = this.factory.build(FISICA, spec);
        assertTrue(fexam.isPresent(), "True False Question had everything but still failed");
    }

}
