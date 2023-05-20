package eapli.base.course.domain;

import java.util.Date;

import javax.persistence.*;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.functional.Either;
import eapli.framework.validations.Preconditions;

@Entity
@Table(name = "COURSE")
public class Course implements AggregateRoot<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDCOURSE")
    private int code;
    @Column(name = "COURSENAME", nullable = false, unique = true)
    private CourseName name;
    @Column(name = "COURSEDESCRIPTION")
    private CourseDescription description;
    @Column(name = "COURSESTATE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseState state;
    @Column(name = "COURSEDURATION")
    private CourseDuration duration;
    @Column(name = "COURSECAPACITY")
    private CourseCapacity capacity;
    @ManyToOne
    private Teacher headTeacher;

    // JPA needs empty constructor
    protected Course() {
    }

    public Course(CourseName name, CourseDescription description, Date startDate, Date endDate) {
        Preconditions.noneNull(name, description, startDate, endDate);

        this.state = CourseState.CLOSE;
        this.name = name;
        this.description = description;
        this.duration = new CourseDuration(startDate, endDate);
        this.capacity = new CourseCapacity();
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

    protected void createdCourse() {
        this.state = CourseState.CLOSE;
    }

    public CourseName name() {
        return this.name;
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

    protected CourseCapacity capacity() {
        return this.capacity;
    }

    public Teacher headTeacher() {
        return this.headTeacher;
    }

    public void setHeadTeacher(Teacher headTeacher) {
        this.headTeacher = headTeacher;
    }

    public boolean setCapacity(int min, int max) {
        try {
            capacity = new CourseCapacity(min, max);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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
        return this.name.equals(o.name)
                && this.code == o.code;
    }

    // TODO:TEST
    @Override
    public int compareTo(Integer other) {
        return AggregateRoot.super.compareTo(other);
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
        return "Course: " +
                "\ncode: " + code +
                "\nname: " + name +
                "\ndescription: " + description +
                "\nduration: " + duration.getStartDate() + " - " + duration.getEndDate();
    }

}
