package eapli.base.exam.domain.regular_exam.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Optional;

@Embeddable
public class RegularExamHeaderDescription implements ValueObject {

    private static final long serialVersionUID = 1L;
    private Optional<String> description;

    public RegularExamHeaderDescription(String description)
    {
        this.description = Optional.ofNullable(description);
    }

    //for ORM
    protected RegularExamHeaderDescription() {
        description = null;
    }

    public static RegularExamHeaderDescription valueOf(final String headerDescription) {
        return new RegularExamHeaderDescription(headerDescription);
    }

    @Override
    public String toString() {return this.description.orElse("");}


}

