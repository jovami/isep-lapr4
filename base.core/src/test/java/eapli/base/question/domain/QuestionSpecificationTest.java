package eapli.base.question.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.IntStream;

import org.junit.Test;

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
                .map(" "::repeat)
                .forEach(str -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new QuestionSpecification(str)));
    }
}
