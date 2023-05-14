package eapli.base.enrollment.domain;

import eapli.base.course.domain.CourseName;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;

@Entity
@Table(name = "ENROLLMENT",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "COURSENAME", "USERNAME" }) })
public class Enrollment implements AggregateRoot<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IDENROLLMENTREQUEST")
    private int code;

    @Column(name="COURSENAME",nullable = false)
    private CourseName courseName;

    // TODO: username vs mecanographicNumber
    @Column(name="USERNAME",nullable = false)
    private Username username;

    public Enrollment(){
        //empty constructor for JPA
    }

    public Enrollment(CourseName courseName, Username username){
        Preconditions.nonNull(courseName, "Course name cannot be null");
        Preconditions.nonNull(username, "Username cannot be null");

        this.courseName = courseName;
        this.username = username;
    }

    public void changeCourseName(CourseName courseName){
        Preconditions.nonNull(courseName, "Course name cannot be null");
        this.courseName = courseName;
    }

    public String obtainCourseName(){
        return this.courseName.getName();
    }

    public void changeUsername(Username username){
        Preconditions.nonNull(username, "Username cannot be null");
        this.username = username;
    }

    public String obtainUsername(){
        return this.username.toString();
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Enrollment)) {
            return false;}

        final Enrollment o = (Enrollment) other;
        if (this == o) {
            return true;}
        return this.courseName.equals(o.courseName)
                && this.username.equals(o.username);
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
        return "Enrollment{" +
                "code=" + code +
                ", courseName=" + courseName +
                ", username=" + username +
                '}';
    }
}
