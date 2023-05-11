package eapli.base.formativeexam.domain;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

@Embeddable
public class FormativeExamTitle implements ValueObject {
    private static final long serialVersionUID = 1L;

    private final String title;

    public FormativeExamTitle(String title) {
        Preconditions.nonEmpty(title, "Formative exam title should not be empty or null");
        this.title = title;
    }

    protected FormativeExamTitle() {
        title = null;
    }

    public static FormativeExamTitle valueOf(final String title) {
        return new FormativeExamTitle(title);
    }

    @Override
    public String toString() {
        return this.title;
    }
}
