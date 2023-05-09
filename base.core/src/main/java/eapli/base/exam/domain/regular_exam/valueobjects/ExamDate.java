package eapli.base.exam.domain.regular_exam.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Collection;
import java.util.Date;

@Embeddable
public class ExamDate implements ValueObject {

    private static final long serialVersionUID = 1L;

    private Date openDate;

    private Date closeDate;

    protected ExamDate(Date openDate, Date closeDate) {
        Preconditions.nonEmpty((Collection<?>) openDate, "Exam open date should not be empty or null");
        Preconditions.nonEmpty((Collection<?>) closeDate, "Exam close date should not be empty or null");

        this.openDate = openDate;
        this.closeDate = closeDate;
    }

    //for ORM
    protected ExamDate() {
        //for ORM only
    }

    public static ExamDate valueOf(Date openDate, Date closeDate) {return new ExamDate(openDate,closeDate);}

    @Override
    public String toString() {
        return "ExamDate{" +
                "openDate=" + openDate +
                ", closeDate=" + closeDate +
                '}';
    }
}
