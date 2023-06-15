package eapli.base.exam.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RegularExamTitle implements ValueObject, Comparable<RegularExamTitle> {
    private final String title;

    public RegularExamTitle() {
        this.title = null;
    }

    protected RegularExamTitle(String title) {
        Preconditions.nonNull(title, "Title should not be null");
        Preconditions.nonEmpty(title, "Title should not be empty");

        this.title = title;
    }

    public String title() {
        return this.title;
    }

    public static RegularExamTitle valueOf(String title) {
        return new RegularExamTitle(title);
    }

    @Override
    public int compareTo(RegularExamTitle o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RegularExamTitle that = (RegularExamTitle) o;
        return this.title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return this.title;
    }
}
