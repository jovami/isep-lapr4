package eapli.base.formativeexam.domain;

import java.util.Optional;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;

@Embeddable
public class FormativeExamDescription implements ValueObject {

    private static final long serialVersionUID = 1L;

    private final Optional<String> description;

    public FormativeExamDescription(String description) {
        this.description = Optional.ofNullable(description);
    }

    protected FormativeExamDescription() {
        this.description = null;
    }

    public static FormativeExamDescription valueOf(final String description) {
        return new FormativeExamDescription(description);
    }

    @Override
    public String toString() {
        return this.description.orElse("");
    }
}
