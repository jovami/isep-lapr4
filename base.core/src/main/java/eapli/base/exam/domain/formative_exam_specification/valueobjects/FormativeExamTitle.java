package eapli.base.exam.domain.formative_exam_specification.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class FormativeExamTitle implements ValueObject {
    private static final long serialVersionUID = 1L;

    private final String formativeExamTitle;

    protected FormativeExamTitle(String formativeExamTitle)
    {
        Preconditions.nonEmpty(formativeExamTitle, "Formative exam title should not be empty or null");

        //TODO: verify regex
        Invariants.ensure(formativeExamTitle.matches("[A-Z]"), "Invalid formative exam title");

        this.formativeExamTitle = formativeExamTitle;
    }

    //for ORM
    protected FormativeExamTitle()
    {
        formativeExamTitle = null;
    }

    public static FormativeExamTitle valueOf(final String formativeExamTitle)
    {
        return new FormativeExamTitle(formativeExamTitle);
    }

    @Override
    public String toString()
    {
        return formativeExamTitle;
    }

}
