package eapli.base.exam.domain.formative_exam_specification.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class FormativeExamDescription implements ValueObject {

    private static final long serialVersionUID = 1L;

    private final String formativeExamDescription;

    protected FormativeExamDescription(String formativeExamDescription)
    {
        Preconditions.nonEmpty(formativeExamDescription, "Formative exam description should not be empty or null");

        //TODO: verify regex
        Invariants.ensure(formativeExamDescription.matches("[A-Z]"), "Invalid formative exam description");

        this.formativeExamDescription = formativeExamDescription;
    }

    //for ORM
    protected FormativeExamDescription()
    {
        formativeExamDescription = null;
    }

    public static FormativeExamDescription valueOf(final String formativeExamDescription)
    {
        return new FormativeExamDescription(formativeExamDescription);
    }

    @Override
    public String toString()
    {
        return formativeExamDescription;
    }

}
