package eapli.base.event.recurringPattern.domain;

import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "RECURRINGPATTERN")
public class RecurringPattern implements AggregateRoot<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int code;

    @Enumerated(EnumType.STRING)
    private RecurringFrequency frequency;

    private LocalDate startDate;

    @Override
    public String toString() {
        return "RecurringPattern{" +
                "code=" + code +
                ", frequency=" + frequency +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", durationMinutes=" + durationMinutes +
                '}';
    }

    private LocalDate endDate;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private int durationMinutes;
    @ElementCollection(targetClass = LocalDate.class)
    private List<LocalDate> exceptions;

    public RecurringPattern() {
        exceptions = new ArrayList<>();
    }

    public RecurringFrequency getFrequency() {
        return frequency;
    }

    public boolean  setTime(LocalTime startTime, int durationMinutes) {
        if (setDurationMinutes(durationMinutes)) {
            setStartTime(startTime);
            return true;
        } else
            return false;
    }

    public boolean addException(LocalDate date) {
        if (this.betweenDates(date)) {
            if (date.getDayOfWeek() == this.dayOfWeek) {
                exceptions.add(date);
                return true;
            }
        }
        return false;
    }

    // True if there is no overlapping between 2 RecurringPatterns
    public boolean overLap(RecurringPattern that) {

        // different weekDays -> false
        if (dayOfWeek != that.dayOfWeek) {
            return false;
        }

        // if dates dont overlap-> false
        if (!overLapDateInterval(that.startDate, that.endDate)) {
            return false;
        }

        // if times dont overlap-> return false
        if (!overLapTime(that)) {
            return false;
        }

        if (that.frequency == RecurringFrequency.ONCE) {
            for (LocalDate ex : exceptions) {
                if (that.betweenDates(ex)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean betweenDates(LocalDate ex) {
        return (startDate.isBefore(ex) || startDate.isEqual(ex)) && (endDate.isAfter(ex) || endDate.isEqual(ex));
    }

    public void setFrequency(RecurringFrequency frequency) {
        this.frequency = frequency;
    }

    public boolean overLapTime(RecurringPattern that) {
        if (startTime.isAfter(that.endTime()) || endTime().isBefore(that.startTime)) {
            return false;
        }

        return true;
    }

    public boolean overLapDateInterval(LocalDate startDate1, LocalDate endDate1) {
        // DATA | DATA INICIO | DATA FIM
        // data 1 | 23-06-2010 | 23-07-2010
        // data 1 | 23-08-2010 | 23-09-2010

        // nao convergem
        if (this.startDate.isAfter(endDate1) || this.endDate.isBefore(startDate1))
            return false;// nao convergem

        return true;// convergem
    }

    public boolean setDateInterval(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(endDate)) {
            setStartDate(startDate);
            setEndDate(endDate);
            return true;
        }
        return false;
    }

    public void setDateOnce(LocalDate date) {
        setStartDate(date);
        setEndDate(date);
        setDayOfWeek(date.getDayOfWeek());
    }

    public LocalDate startDate() {
        return startDate;
    }

    private void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    private void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public DayOfWeek dayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime startTime() {
        return startTime;
    }

    private void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int duration() {
        return durationMinutes;
    }

    public boolean setDurationMinutes(int durationMinutes) {
        if (durationMinutes < 10) {
            return false;
        }
        this.durationMinutes = durationMinutes;
        return true;
    }

    public LocalTime endTime() {
        return startTime.plusMinutes(durationMinutes);
    }

    @Override
    public boolean sameAs(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RecurringPattern that = (RecurringPattern) o;
        return code == that.code &&
                durationMinutes == that.durationMinutes &&
                frequency == that.frequency &&
                dayOfWeek == that.dayOfWeek &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, frequency, startDate, endDate, dayOfWeek, startTime, durationMinutes, exceptions);
    }

    @Override
    public int compareTo(Integer other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Integer identity() {
        return code;
    }

    @Override
    public boolean hasIdentity(Integer id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
