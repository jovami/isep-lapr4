package jovami.grammar.impl.antlr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseFactory;
import eapli.base.course.dto.CreateCourseDTO;
import eapli.base.formativeexam.domain.FormativeExamFactory;

/**
 * FormativeExamFactoryTest
 */
public class FormativeExamFactoryTest {
    private Course FISICA = null;
    private final String TITLE = "Teste";

    private FormativeExamFactory factory;

    @Before
    public void makeFactory() throws Exception {
        this.factory = new FormativeExamFactory(new ANTLRFormativeExamValidator());
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
    public void ensureSpecificationMustHaveTitle() {
        // @formatter:off
        var spec = List.of(
                "FORMATIVE EXAM {",
                // missing title
                "SECTION 1 {",
                "QUESTION 1: MATCHING",
                "}",
                "}");
        // @formatter:on

        var fexam = this.factory.build(TITLE, FISICA, spec);
        assertTrue(fexam.isEmpty(), "Specification must have a title");
    }

    @Test
    public void ensureSpecificationMustHaveAtLeastOneSection() {
        // @formatter:off
        var spec = List.of(
                "FORMATIVE EXAM {",
                "TITLE: \"Fisica 1\"",
                "HEADER {",
                "DESCRIPTION: \"Fisica dos materiais --- Isolantes termicos\"",
                "}",
                // missing section(s)
                "}");
        // @formatter:on

        var fexam = this.factory.build(TITLE, FISICA, spec);
        assertTrue(fexam.isEmpty(), "Specification must have at least one section");

        // @formatter:off
        spec = List.of(
                "FORMATIVE EXAM {",
                "TITLE: \"Fisica 1\"",
                "HEADER {",
                "DESCRIPTION: \"Fisica dos materiais --- Isolantes termicos\"",
                "}",

                "SECTION 1 {",
                "DESCRIPTION: \"Escolhas multiplas\"",
                "QUESTION 1: TRUE_FALSE",
                "QUESTION 2: TRUE_FALSE",
                "QUESTION 3: TRUE_FALSE",
                "QUESTION 4: TRUE_FALSE",
                "QUESTION 5: TRUE_FALSE",
                "}",
                "}");
        // @formatter:on

        fexam = this.factory.build(TITLE, FISICA, spec);
        assertTrue(fexam.isPresent(), "Specification had one section but still failed parsing");

        // @formatter:off
        spec = List.of(
                "FORMATIVE EXAM {",
                "TITLE: \"Fisica 1\"",
                "HEADER {",
                "DESCRIPTION: \"Fisica dos materiais --- Isolantes termicos\"",
                "}",

                "SECTION 1 {",
                "DESCRIPTION: \"Escolhas multiplas\"",
                "QUESTION 1: TRUE_FALSE",
                "QUESTION 2: TRUE_FALSE",
                "QUESTION 3: TRUE_FALSE",
                "QUESTION 4: TRUE_FALSE",
                "QUESTION 5: TRUE_FALSE",
                "}",
                "SECTION 2 {",
                // has section description
                "DESCRIPTION: \"Mathing HELL\"",
                "QUESTION 1: MATCHING",
                "QUESTION 2: MATCHING",
                "QUESTION 3: MATCHING",
                "QUESTION 4: MATCHING",
                "QUESTION 5: NUMERICAL",
                "QUESTION 5: NUMERICAL",
                "QUESTION 5: SHORT_ANSWER",
                "QUESTION 5: MULTIPLE_CHOICE",
                "}",
                "}");
        // @formatter:on

        fexam = this.factory.build(TITLE, FISICA, spec);
        assertTrue(fexam.isPresent(), "Specification had two section but still failed parsing");
    }

    @Test
    public void ensureSpecificationSectionsMustHaveAtLeastOneQuestionType() {
        // @formatter:off
        var spec = List.of(
                "FORMATIVE EXAM {",
                "TITLE: \"Fisica 1\"",
                "HEADER {",
                "DESCRIPTION: \"Fisica dos materiais --- Isolantes termicos\"",
                "}",
                "SECTION 1 {",
                "DESCRIPTION: \"Escolhas multiplas\"",
                // No question types
                "}",
                "}");
        // @formatter:on

        var fexam = this.factory.build(TITLE, FISICA, spec);
        assertTrue(fexam.isEmpty(), "Specification must have a title");
    }

    @Test
    public void ensureSpecificationDescriptionCanBeOptional() {
        var specs = new ArrayList<List<String>>();

        {
            // @formatter:off
            var spec = List.of(
                    "FORMATIVE EXAM {",
                    "TITLE: \"Fisica 1\"",
                    // has global description
                    "HEADER {",
                    "DESCRIPTION: \"Fisica dos materiais --- Isolantes termicos\"",
                    "}",
                    "SECTION 1 {",
                    // has section description
                    "DESCRIPTION: \"Escolhas multiplas\"",
                    "QUESTION 1: TRUE_FALSE",
                    "}",
                    "}");
            // @formatter:on
            specs.add(spec);
        }

        {
            // @formatter:off
            var spec = List.of(
                    "FORMATIVE EXAM {",
                    "TITLE: \"Fisica 1\"",
                    // no global description
                    "SECTION 1 {",
                    // has section description
                    "DESCRIPTION: \"Escolhas multiplas\"",
                    "QUESTION 1: TRUE_FALSE",
                    "}",
                    "}");
            // @formatter:on
            specs.add(spec);
        }

        {
            // @formatter:off
            var spec = List.of(
                    "FORMATIVE EXAM {",
                    "TITLE: \"Fisica 1\"",
                    // has no global description
                    "SECTION 1 {",
                    // has section description
                    "DESCRIPTION: \"Escolhas multiplas\"",
                    "QUESTION 1: TRUE_FALSE",
                    "}",
                    "}");
            // @formatter:on
            specs.add(spec);
        }

        {
            // @formatter:off
            var spec = List.of(
                    "FORMATIVE EXAM {",
                    "TITLE: \"Fisica 1\"",
                    // has no global description
                    "SECTION 1 {",
                    // has no section description
                    "DESCRIPTION: \"Escolhas multiplas\"",
                    "QUESTION 1: TRUE_FALSE",
                    "}",
                    "SECTION 2 {",
                    // has section description
                    "DESCRIPTION: \"Exemplo\"",
                    "QUESTION 1: MATCHING",
                    "QUESTION 2: TRUE_FALSE",
                    "QUESTION 3: SHORT_ANSWER",
                    "}",
                    "}");
            // @formatter:on
            specs.add(spec);
        }

        specs.stream()
                .map(spec -> this.factory.build(TITLE, FISICA, spec))
                .forEach(fexam -> assertTrue(fexam.isPresent(), "Specification must have a title"));
    }

    @Test
    public void ensureInvalidQuestionTypesAreNotAllowed() {
        // @formatter:off
        var spec = List.of(
                "FORMATIVE EXAM {",
                "TITLE: \"Fisica 1\"",
                "HEADER {",
                "DESCRIPTION: \"Fisica dos materiais --- Isolantes termicos\"",
                "}",

                "SECTION 1 {",
                "DESCRIPTION: \"Escolhas multiplas\"",
                "QUESTION 1: TRUE_FALSE",
                "QUESTION 2: TRUE_FALSE",
                "QUESTION 3: TRUE_FALSE",
                "QUESTION 4: TRUE_FALSE",
                "QUESTION 5: TRUE_FALSE",
                "}",
                "SECTION 2 {",
                "DESCRIPTION: \"Escolhas multiplas\"",
                "QUESTION 1: FALSE_TRUE", // question type does not exist
                "QUESTION 2: FALSE_TRUE",
                "QUESTION 3: FALSE_TRUE",
                "QUESTION 4: FALSE_TRUE",
                "QUESTION 5: FALSE_TRUE",
                "}",
                "}");
        // @formatter:on

        var fexam = this.factory.build(TITLE, FISICA, spec);
        assertTrue(fexam.isEmpty(), "Sections must have valid question types");
    }
}
