package eapli.base.exam.domain.regular_exam.antlr;

import java.util.List;
import java.util.Objects;

public class Section {
    private int id;
    private final String description;
    private final List<Question> questions;

    public Section(int id, String description, List<Question> questions) {
        this.id = id;
        this.description = description;
        this.questions = questions;
    }

    public int id() {
        return this.id;
    }

    public String description() {
        return this.description;
    }

    public List<Question> questions() {
        return this.questions;
    }

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
