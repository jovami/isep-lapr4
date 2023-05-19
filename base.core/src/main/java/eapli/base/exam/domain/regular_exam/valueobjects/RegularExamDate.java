package eapli.base.exam.domain.regular_exam.valueobjects;

import eapli.base.course.domain.CourseName;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class RegularExamDate implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.DATE)
    private Date openDate;

    @Temporal(TemporalType.DATE)
    private Date closeDate;

    protected RegularExamDate()
    {
        this.openDate = null;
        this.closeDate = null;
    }

    public RegularExamDate(Date openDate, Date closeDate) {
        setIntervalDate(openDate,closeDate);
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

    public Date openDate()
    {
        return this.openDate;
    }

    public Date closeDate()
    {
        return this.closeDate;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegularExamDate that = (RegularExamDate) o;
        return this.openDate.equals(that.openDate) && this.closeDate.equals(that.closeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openDate);
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String open = dateFormat.format(openDate);
        String close = dateFormat.format(closeDate);
        return "Opening: " + open +
                " | Closing: " + close;
    }
}
