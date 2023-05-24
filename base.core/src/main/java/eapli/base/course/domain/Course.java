package eapli.base.course.domain;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.functional.Either;
import eapli.framework.validations.Preconditions;

@Entity
public class Course implements AggregateRoot<CourseID> {

    @EmbeddedId
    private final CourseID id;

    @Column(nullable = false)
    private final CourseDescription description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseState state;

    @Column(nullable = false)
    private final CourseDuration duration;

    @Column(nullable = false)
    private final CourseCapacity capacity;

    @ManyToOne
    private Teacher headTeacher;

    // JPA needs empty constructor
    protected Course() {
        this.id = null;
        this.description = null;
        this.duration = null;
        this.capacity = null;
    }

    protected Course(CourseID id, CourseDescription description, CourseDuration duration, CourseCapacity capacity) {
        Preconditions.noneNull(id, description, duration, capacity);

        this.id = id;
        this.description = description;
        this.duration = duration;
        this.capacity = capacity;

        this.state = CourseState.CLOSE;
    }

    public Either<String, CourseState> close() {
        if (this.state == CourseState.CLOSED)
            return Either.left("Course is already closed");
        Either<String, CourseState> old = Either.right(this.state);
        this.state = CourseState.CLOSED;
        return old;
    }

    public Either<String, CourseState> openEnrollments() {
        switch (this.state) {
            case OPEN:
                this.state = CourseState.ENROLL;
                return Either.right(CourseState.OPEN);
            case ENROLL:
                return Either.left("Course is already open to enrollments");
            default:
                return Either.left("Course cannot be opened to enrollments in its current state");
        }
    }

    public Either<String, CourseState> open() {
        switch (this.state) {
            case CLOSE:
                this.state = CourseState.OPEN;
                return Either.right(CourseState.CLOSE);
            case OPEN:
                return Either.left("Course is already open");
            default:
                return Either.left("Course cannot be opened in its current state");
        }
    }

    public Either<String, CourseState> closeEnrollments() {
        switch (this.state) {
            case ENROLL:
                this.state = CourseState.INPROGRESS;
                return Either.right(CourseState.ENROLL);
            case INPROGRESS:
                return Either.left("Course is already in progress");
            default:
                return Either.left("Course cannot be opened to enrollments in its current state");
        }
    }

    public CourseDescription description() {
        return this.description;
    }

    public CourseState state() {
        return this.state;
    }

    protected CourseDuration duration() {
        return this.duration;
    }

    public Optional<CourseCapacity> capacity() {
        return Optional.ofNullable(this.capacity);
    }

    public Optional<Teacher> headTeacher() {
        return Optional.ofNullable(this.headTeacher);
    }

    public void setHeadTeacher(Teacher headTeacher) {
        this.headTeacher = headTeacher;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Course)) {
            return false;
        }

        final Course o = (Course) other;
        if (this == o) {
            return true;
        }
        return this.id.equals(o.id);
    }

    // TODO:TEST
    @Override
    public int compareTo(CourseID other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public CourseID identity() {
        return this.id;
    }

    @Override
    public boolean hasIdentity(CourseID id) {
        return AggregateRoot.super.hasIdentity(id);
    }

    @Override
    public String toString() {
        return "Course: " +
                "\nID: " + this.id +
                "\nDescription: " + description +
                "\nDuration: " + duration;
    }
}
