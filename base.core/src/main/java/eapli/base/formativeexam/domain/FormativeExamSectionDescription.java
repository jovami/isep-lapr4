package eapli.base.formativeexam.domain;

import java.util.Optional;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;

/**
 * FormativeExamSectionDescription
 */
@Embeddable
public class FormativeExamSectionDescription implements ValueObject {

    private final Optional<String> description;

    protected FormativeExamSectionDescription() {
        this.description = null;
    }

    public FormativeExamSectionDescription(String description) {
        this.description = Optional.ofNullable(description);
    }

    @Override
    public String toString() {
        return this.description.orElse("");
    }
}
