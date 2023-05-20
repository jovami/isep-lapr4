package eapli.base.event.lecture.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.framework.domain.model.AggregateRoot;

@Entity
public class LectureParticipant implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LECTUREPARTICIPANTID")
    private int id;
    @ManyToOne
    private Lecture lecture;
    @ManyToOne

    private Student student;

    protected LectureParticipant() {

    }

    public LectureParticipant(Student student, Lecture lecture) {
        this.student = student;
        this.lecture = lecture;
    }

    public Lecture lecture() {
        return this.lecture;
    }

    @Override
    public boolean sameAs(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LectureParticipant that = (LectureParticipant) o;
        return Objects.equals(lecture, that.lecture) && Objects.equals(student, that.student);
    }

    @Override
    public int compareTo(Integer other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Integer identity() {
        return this.id;
    }

    @Override
    public boolean hasIdentity(Integer id) {
        return AggregateRoot.super.hasIdentity(id);
    }

    @Override
    public String toString() {
        return "\nLectureParticipant: " +
                "with id: " + id +
                ", with " + lecture.toString() +
                ", with student: " + student.identity();
    }

    public Student studentParticipant(){
        return student;
    }
}
