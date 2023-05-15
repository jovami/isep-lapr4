package eapli.base.enrollmentrequest.domain;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.functional.Either;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;

@Entity
@Table(name="ENROLLMENTREQUEST",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "COURSE", "STUDENT" }) })
public class EnrollmentRequest implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IDENROLLMENTREQUEST")
    private int code;

    @Column(name="COURSE",nullable = false)
    @ManyToOne
    private Course course;

    // TODO: username vs mecanographicNumber
    @Column(name="STUDENT",nullable = false)
    @ManyToOne
    private Student student;

    @Column(name="ENROLLMENTREQUESTSTATE",nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentRequestState state;

    @Column(name="DENIEDREASON")
    private DeniedReason deniedReason;

    public EnrollmentRequest(){
        //for JPA
    }

    public EnrollmentRequest(Course course, Student student){
        Preconditions.nonNull(course, "Course name cannot be null");
        Preconditions.nonNull(student, "Username cannot be null");
        this.state = EnrollmentRequestState.PENDING;
        this.deniedReason = new DeniedReason(); // starts with null description value

        this.course = course;
        this.student = student;
    }

    public Either<String, EnrollmentRequestState> approveEnrollmentRequest() {
        switch (this.state) {
            case PENDING:
                this.state = EnrollmentRequestState.APPROVED;
                return Either.right(EnrollmentRequestState.PENDING);
            case APPROVED:
                return Either.left("Enrollment request was already approved");
            default:
                return Either.left("Enrollment request was already denied");
        }
    }

    public Either<String, EnrollmentRequestState> denyEnrollmentRequest(String deniedReason) {
        switch (this.state) {
            case PENDING:
                this.state = EnrollmentRequestState.DENIED;
                this.deniedReason.setDenyingReason(deniedReason);
                return Either.right(EnrollmentRequestState.PENDING);
            case APPROVED:
                return Either.left("Enrollment request was already approved");
            default:
                return Either.left("Enrollment request was already denied");
        }
    }

    public Either<String, String> getDeniedReason() {
        if (this.state == EnrollmentRequestState.DENIED) {
            return Either.right(this.deniedReason.getDenyingReason());
        }
        return Either.left("Enrollment request was not denied");
    }

    public EnrollmentRequestState getEnrollmentRequestState() {
        return state;
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof EnrollmentRequest)) {
            return false;}

        final EnrollmentRequest o = (EnrollmentRequest) other;
        if (this == o) {
            return true;}
        return this.course.equals(o.course)
                && this.student.equals(o.student);
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
        return "EnrollmentRequest{" +
                "code=" + code +
                ", course=" + course +
                ", student=" + student +
                ", state=" + state +
                ", deniedReason=" + deniedReason +
                '}';
    }
}
