package eapli.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
@Embeddable
public class CourseDuration implements ValueObject {
    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    protected CourseDuration(){
        startDate=null;
        endDate=null;
    }
    protected  CourseDuration(Date startDate,Date endDate){
        setIntervalDate(startDate,endDate);
    }

    protected boolean setIntervalDate(Date startDate, Date endDate) {
        if(startDate.before(endDate)){
            this.startDate=startDate;
            this.endDate=endDate;
            return true;
        }
        return false;
    }

    protected Date startDate(){
        return startDate;
    }

    @Override
    public String toString() {
        return "Start date: "+startDate.toString()+
                "\tEnd date: "+endDate.toString();
    }

    protected Date endDate(){
        return endDate;

    }
}
