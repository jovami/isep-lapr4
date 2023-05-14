package eapli.base.enrollmentrequest.domain;

import eapli.base.course.domain.CourseName;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.functional.Either;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;

@Entity
@Table(name="ENROLLMENTREQUEST",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "COURSENAME", "USERNAME" }) })
public class EnrollmentRequest implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IDENROLLMENTREQUEST")
    private int code;

    @Column(name="COURSENAME",nullable = false)
    private CourseName courseName;

    // TODO: username vs mecanographicNumber
    @Column(name="USERNAME",nullable = false)
    private Username username;

    @Column(name="ENROLLMENTREQUESTSTATE",nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentRequestState state;

    @Column(name="DENIEDREASON")
    private DeniedReason deniedReason;

    public EnrollmentRequest(){
        //for JPA
    }

    public EnrollmentRequest(CourseName courseName, Username username){
        Preconditions.nonNull(courseName, "Course name cannot be null");
        Preconditions.nonNull(username, "Username cannot be null");
        this.state = EnrollmentRequestState.PENDING;
        this.deniedReason = new DeniedReason(); // starts with null description value

        this.courseName = courseName;
        this.username = username;
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

    public CourseName getCourseName() {
        return courseName;
    }

    public Username getUsername() {
        return username;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof EnrollmentRequest)) {
            return false;}

        final EnrollmentRequest o = (EnrollmentRequest) other;
        if (this == o) {
            return true;}
        return this.courseName.equals(o.courseName)
                && this.username.equals(o.username);}

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
                ", courseName=" + courseName +
                ", username=" + username +
                ", state=" + state +
                ", deniedReason=" + deniedReason +
                '}';
    }
}
