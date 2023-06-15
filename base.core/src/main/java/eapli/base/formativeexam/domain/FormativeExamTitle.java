package eapli.base.formativeexam.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class FormativeExamTitle implements ValueObject, Comparable<FormativeExamTitle> {
    private final String title;

    public FormativeExamTitle() {
        this.title = null;
    }

    protected FormativeExamTitle(String title) {
        Preconditions.nonNull(title, "Title should not be null");
        Preconditions.nonEmpty(title, "Title should not be empty");

        this.title = title;
    }

    public String title() {
        return this.title;
    }

    public static FormativeExamTitle valueOf(String title) {
        return new FormativeExamTitle(title);
    }

    @Override
    public int compareTo(FormativeExamTitle o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FormativeExamTitle that = (FormativeExamTitle) o;
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
