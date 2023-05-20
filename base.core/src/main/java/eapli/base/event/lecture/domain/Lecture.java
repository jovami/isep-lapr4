package eapli.base.event.lecture.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.framework.domain.model.AggregateRoot;

@Entity
@Table(name = "LECTURE")
public class Lecture implements AggregateRoot<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LECTUREID")
    private int lectureId;

    @OneToOne
    private Teacher teacher;

    @OneToOne
    private RecurringPattern pattern;

    @Enumerated(EnumType.STRING)
    private LectureType type;

    // TODO: LECTURE--COURSE MUST BE REFERENCED

    protected Lecture() {
    }

    public Lecture(Teacher teacher, RecurringPattern pattern) {
        this.teacher = teacher;
        this.pattern = pattern;
    }

    public void regular() {
        this.type = LectureType.REGULAR;
    }

    public void extra() {
        this.type = LectureType.EXTRA;
    }

    public LectureType type() {
        return this.type;
    }

    public Teacher teacher() {
        return teacher;
    }

    public RecurringPattern pattern() {
        return pattern;
    }

    public void updatePattern(RecurringPattern pattern){ this.pattern = pattern;}

    @Override
    public boolean sameAs(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Lecture meeting = (Lecture) o;
        return Objects.equals(lectureId, meeting.lectureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectureId, teacher, pattern);
    }


    @Override
    public int compareTo(Integer other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Integer identity() {
        return this.lectureId;
    }

    @Override
    public boolean hasIdentity(Integer id) {
        return this.lectureId == id;
    }

    @Override
    public String toString() {
        return "Lecture: " +
                "Id: " + lectureId +
                ", teacher: " + teacher.acronym() +
                ", pattern: " + pattern.startTime() +" to "+ pattern.endTime();
    }

}
