package eapli.base.exam.domain.regular_exam.valueobjects;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Optional;

@Embeddable
public class RegularExamSpecification implements ValueObject {

    private static final long serialVersionUID = 1L;
    private Optional<String> regularExamSpecification;

    public RegularExamSpecification(String description)
    {
        this.regularExamSpecification = Optional.ofNullable(description);
    }

    //for ORM
    protected RegularExamSpecification() {
        regularExamSpecification = null;
    }

    public static RegularExamSpecification valueOf(final String regularExamSpecification) {
        return new RegularExamSpecification(regularExamSpecification);
    }

    @Override
    public String toString() {return this.regularExamSpecification.orElse("");}


}

