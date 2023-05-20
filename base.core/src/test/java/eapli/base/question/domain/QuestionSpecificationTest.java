package eapli.base.question.domain;

import eapli.base.formativeexam.domain.FormativeExamSpecification;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * FormativeExamSpecificationTest
 */
public class QuestionSpecificationTest {

    @Test
    public void ensureSpecificationCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new QuestionSpecification(null));
    }

    @Test
    public void ensureSpecificationCannotBeEmpty() {
        // Test a couple of empty strings
        IntStream
                .range(0, 100)
                .boxed()
                .map(""::repeat)
                .forEach(str -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new QuestionSpecification(str)));
    }
}
