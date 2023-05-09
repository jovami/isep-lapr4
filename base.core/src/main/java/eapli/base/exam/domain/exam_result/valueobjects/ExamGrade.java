package eapli.base.exam.domain.exam_result.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Collections;

@Embeddable
public class ExamGrade implements ValueObject {

    private static final long serialVersionUID = 1L;

    private final Float examGrade;

    protected ExamGrade(Float examGrade)
    {
        Preconditions.nonEmpty(Collections.singleton(examGrade), "Exam Grade shouldn't be empty");

        this.examGrade = examGrade;
    }

    //for ORM
    protected ExamGrade() {
        examGrade = null;
    }

    public static ExamGrade valueOf(final Float examGrade){return new ExamGrade(examGrade);}

    @Override
    public String toString(){return String.valueOf(examGrade);}



}
