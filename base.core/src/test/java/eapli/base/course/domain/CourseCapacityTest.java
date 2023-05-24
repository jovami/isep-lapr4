package eapli.base.course.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

/**
 * CourseCapacityTest
 */
public class CourseCapacityTest {

    @Test
    public void ensureCapacityMustBePositive() {
        assertThrows(IllegalArgumentException.class, () -> CourseCapacity.valueOf(-10, -5));
        assertThrows(IllegalArgumentException.class, () -> CourseCapacity.valueOf(-10, 0));
        assertThrows(IllegalArgumentException.class, () -> CourseCapacity.valueOf(-10, 22));

        assertThrows(IllegalArgumentException.class, () -> CourseCapacity.valueOf(0, -5));
        assertThrows(IllegalArgumentException.class, () -> CourseCapacity.valueOf(0, 0));

        assertThrows(IllegalArgumentException.class, () -> CourseCapacity.valueOf(22, -10));
        assertThrows(IllegalArgumentException.class, () -> CourseCapacity.valueOf(22, 0));

        assertDoesNotThrow(() -> CourseCapacity.valueOf(14, 22));
        assertDoesNotThrow(() -> CourseCapacity.valueOf(94, 122));
    }

    @Test
    public void ensureMaximumIsGreaterThanOrEqualToMinimum() {
        assertThrows(IllegalStateException.class, () -> CourseCapacity.valueOf(22, 10));
        assertThrows(IllegalStateException.class, () -> CourseCapacity.valueOf(2, 1));
        assertThrows(IllegalStateException.class, () -> CourseCapacity.valueOf(444, 1));

        assertDoesNotThrow(() -> CourseCapacity.valueOf(1, 1));
        assertDoesNotThrow(() -> CourseCapacity.valueOf(24, 24));

        assertDoesNotThrow(() -> CourseCapacity.valueOf(1, 2));
        assertDoesNotThrow(() -> CourseCapacity.valueOf(14, 22));
        assertDoesNotThrow(() -> CourseCapacity.valueOf(94, 122));
    }
}
