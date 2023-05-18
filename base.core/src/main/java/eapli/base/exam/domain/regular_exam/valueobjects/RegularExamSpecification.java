package eapli.base.exam.domain.regular_exam.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Optional;

@Embeddable
public class RegularExamSpecification implements ValueObject {

    private static final long serialVersionUID = 1L;
    private String specification;

    public RegularExamSpecification(String specification)
    {
        Preconditions.noneNull(specification, "Regular Exam specification cannot be null");
        Preconditions.nonEmpty(specification, "Regular Exam specification cannot be empty");
        this.specification = specification;
    }

    //for ORM
    protected RegularExamSpecification() {
        specification = null;
    }

    public static RegularExamSpecification valueOf(final String regularExamSpecification) {
        return new RegularExamSpecification(regularExamSpecification);
    }

    @Override
    public String toString() { return this.specification; }


}

