package eapli.base.exam.domain;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegularExamTitleTest {

    @Test
    public void ensureExamTitleCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> RegularExamTitle.valueOf(null));
        assertDoesNotThrow(() -> RegularExamTitle.valueOf("valid"));
    }

    @Test
    public void ensureExamTitleCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> RegularExamTitle.valueOf(""));
        assertDoesNotThrow(() -> RegularExamTitle.valueOf("valid"));
    }
}