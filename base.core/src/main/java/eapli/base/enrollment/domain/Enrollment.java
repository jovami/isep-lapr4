package eapli.base.enrollment.domain;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ENROLLMENT",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "COURSE", "STUDENT" }) })
public class Enrollment implements AggregateRoot<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IDENROLLMENTREQUEST")
    private int code;

    @JoinColumn(name="COURSE",nullable = false)
    @ManyToOne
    private Course course;

    @JoinColumn(name="STUDENT",nullable = false)
    @ManyToOne
    private Student student;

    public Enrollment(){
        //empty constructor for JPA
    }

    public Enrollment(Course course, Student student){
        Preconditions.nonNull(course, "Course name cannot be null");
        Preconditions.nonNull(student, "Student cannot be null");

        this.course = course;
        this.student = student;
    }

    public Course course(){
        return this.course;
    }

    public Student student(){
        return this.student;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Enrollment)) {
            return false;}

        final Enrollment o = (Enrollment) other;
        if (this == o) {
            return true;}
        return this.course.sameAs(o.course)
                && this.student.sameAs(o.student);
    }

    @Override
    public Integer identity() {
        return this.code;
    }

    @Override
    public boolean hasIdentity(Integer id) {
        return AggregateRoot.super.hasIdentity(id);
    }

    @Override
    public String toString() {
        return "Enrollment " +
                "code: " + code +
                ", from Course: " + course.getName() +
                ", with Student: " + student.identity();
    }
}
