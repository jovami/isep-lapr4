package eapli.base.enrollmentrequest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.functional.Either;
import eapli.framework.validations.Preconditions;

@Entity
@Table(name = "ENROLLMENTREQUEST", uniqueConstraints = { @UniqueConstraint(columnNames = { "COURSE", "STUDENT" }) })
public class EnrollmentRequest implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENROLLMENTREQUEST")
    private int code;

    @JoinColumn(name = "COURSE", nullable = false)
    @ManyToOne
    private Course course;

    @JoinColumn(name = "STUDENT", nullable = false)
    @ManyToOne
    private Student student;

    @Column(name = "ENROLLMENTREQUESTSTATE", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentRequestState state;

    @Column(name = "DENIEDREASON")
    private DeniedReason deniedReason;

    protected EnrollmentRequest() {
        // for JPA
    }

    public EnrollmentRequest(Course course, Student student) {
        Preconditions.nonNull(course, "Course name cannot be null");
        Preconditions.nonNull(student, "Student cannot be null");

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
                this.deniedReason.specifyDeniedReason(deniedReason);
                return Either.right(EnrollmentRequestState.PENDING);
            case APPROVED:
                return Either.left("Enrollment request was already approved");
            default:
                return Either.left("Enrollment request was already denied");
        }
    }

    public Either<String, DeniedReason> deniedReason() {
        if (this.state == EnrollmentRequestState.DENIED) {
            return Either.right(this.deniedReason);
        }
        return Either.left("Enrollment request was not denied");
    }

    public EnrollmentRequestState enrollmentRequestState() {
        return state;
    }

    public boolean isPending() {
        return state == EnrollmentRequestState.PENDING;
    }

    public Course course() {
        return course;
    }

    public Student student() {
        return student;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof EnrollmentRequest)) {
            return false;
        }

        final EnrollmentRequest o = (EnrollmentRequest) other;
        if (this == o) {
            return true;
        }
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
        return "EnrollmentRequest: " +
                "\ncode: " + code +
                "\ncourse: " + course.toString() +
                "\nstudent: " + student.toString() +
                "\nstate: " + state.toString();
    }

}
