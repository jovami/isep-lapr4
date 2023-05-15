package eapli.base.exam.domain.regular_exam.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;

@Embeddable
public class RegularExamDate implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.DATE)
    private Date openDate;

    @Temporal(TemporalType.DATE)
    private Date closeDate;

    public RegularExamDate(Date openDate, Date closeDate) {
        Preconditions.nonEmpty((Collection<?>) openDate, "Exam open date should not be empty or null");
        Preconditions.nonEmpty((Collection<?>) closeDate, "Exam close date should not be empty or null");

        setIntervalDate(openDate,closeDate);
    }

    protected RegularExamDate()
    {
        this.openDate = null;
        this.closeDate = null;
    }

    public static RegularExamDate valueOf(Date openDate, Date closeDate) {return new RegularExamDate(openDate,closeDate);}

    protected boolean setIntervalDate(Date openDate, Date closeDate) {
        if(openDate.before(closeDate)){
            this.openDate=openDate;
            this.closeDate=closeDate;
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return "RegularExamDate{" +
                "openDate=" + openDate +
                ", closeDate=" + closeDate +
                '}';
    }
}
