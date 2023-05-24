package eapli.base.course.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class CourseIDTest {
    @Test
    public void ensureTitleCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> CourseID.valueOf(null, 333));
        assertDoesNotThrow(() -> CourseID.valueOf("valid", 333));
    }

    @Test
    public void ensureCodeCannotBeNegative() {
        final var title = "Valid";

        assertThrows(IllegalArgumentException.class, () -> CourseID.valueOf(title, -10));
        assertThrows(IllegalArgumentException.class, () -> CourseID.valueOf(title, -30));
        assertThrows(IllegalArgumentException.class, () -> CourseID.valueOf(title, -1));
        assertThrows(IllegalArgumentException.class, () -> CourseID.valueOf(title, -1000302));

        assertDoesNotThrow(() -> CourseID.valueOf(title, 0));
        assertDoesNotThrow(() -> CourseID.valueOf(title, 1));
        assertDoesNotThrow(() -> CourseID.valueOf(title, 2));
        assertDoesNotThrow(() -> CourseID.valueOf(title, 24));
        assertDoesNotThrow(() -> CourseID.valueOf(title, 2455));
        assertDoesNotThrow(() -> CourseID.valueOf(title, 2452115));
    }

    @Test
    public void ensureIDMatchesPattern() {
        assertThrows(NullPointerException.class, () -> CourseID.valueOf(null));
        assertThrows(IllegalStateException.class, () -> CourseID.valueOf("aaaa-123aaa"), "aaaa-123aaa");
        assertThrows(IllegalStateException.class, () -> CourseID.valueOf("aa%^&%aa-123191"), "aa%^&%aa-123191");
        assertThrows(IllegalStateException.class, () -> CourseID.valueOf("123aaaa-1111"), "123aaaa-1111");

        assertDoesNotThrow(() -> CourseID.valueOf("JAVA-1"));
        assertDoesNotThrow(() -> CourseID.valueOf("TESTE-1321"));
        assertDoesNotThrow(() -> CourseID.valueOf("Fisica-112"));
    }
}
