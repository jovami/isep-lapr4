package eapli.base.exam.domain.regular_exam.antlr;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Section {
    private int id;
    private final String description;
    private final List<Question> questions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section section = (Section) o;
        return id == section.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
