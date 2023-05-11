package eapli.base.exam.domain.regular_exam.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class HeaderDescription implements ValueObject {

    private String headerDescription;

    protected HeaderDescription(String headerDescription) {
        Preconditions.nonEmpty(headerDescription, "Header description should not be empty or null");

        //TODO: verify regex
        Invariants.ensure(headerDescription.matches("[A-Z]"), "Invalid header description");

        this.headerDescription = headerDescription;
    }

    //for ORM
    public HeaderDescription() {
        headerDescription = null;
    }

    public static HeaderDescription valueOf(final String headerDescription) {
        return new HeaderDescription(headerDescription);
    }

    @Override
    public String toString() {
        return headerDescription;
    }


}

