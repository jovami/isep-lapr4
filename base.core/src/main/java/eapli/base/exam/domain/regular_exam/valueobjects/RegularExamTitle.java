package eapli.base.exam.domain.regular_exam.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class RegularExamTitle implements ValueObject {

    private static final long serialVersionUID = 1L;
    private String title;

    public RegularExamTitle(String title) {
        Preconditions.nonEmpty(title, "Exam tittle should not be empty or null");

        this.title = title;
    }

    //for ORM
    protected RegularExamTitle() {
        title = null;
    }

    public static RegularExamTitle valueOf(final String examTitle) {return new RegularExamTitle(examTitle);}

    @Override
    public String toString() {
        return title;
    }
}
