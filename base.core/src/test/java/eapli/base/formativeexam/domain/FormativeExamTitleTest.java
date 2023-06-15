package eapli.base.formativeexam.domain;

import eapli.base.exam.domain.RegularExamTitle;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormativeExamTitleTest {

    @Test
    public void ensureExamTitleCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> FormativeExamTitle.valueOf(null));
        assertDoesNotThrow(() -> FormativeExamTitle.valueOf("valid"));
    }

    @Test
    public void ensureExamTitleCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> FormativeExamTitle.valueOf(""));
        assertDoesNotThrow(() -> FormativeExamTitle.valueOf("valid"));
    }

}