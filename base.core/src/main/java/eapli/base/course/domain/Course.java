package eapli.base.course.domain;

import java.util.Date;

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
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.functional.Either;

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

    // TODO: HEAD-TEACHER
    // @Column(name="HEADTEACHER",nullable = false)
    @OneToOne
    private Teacher headTeacher;

    // TODO: ENROLLMENT

    // TODO: FORMATIVE/REGULAR EXAMS

    // TODO: LECTURE--COURSE MUST BE REFERENCED IN THE LECTURE

    // JPA needs empty constructor
    protected Course() {
    }

    public Course(String name, String description, Date startDate, Date endDate) {
        this.state = CourseState.CLOSE;
        this.name = new CourseName(name);
        this.description = new CourseDescription(description);
        this.duration = new CourseDuration(startDate, endDate);
        this.capacity = new CourseCapacity();
    }

    protected void setName(String name) {
        this.name = new CourseName(name);
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

    /*
     * public void setHeadTeacher(Teacher headTeacher){
     * this.headTeacher=headTeacher;
     * }
     */

    public String getName() {
        return name.getName();
    }

    public CourseName courseName() {
        return name;
    }

    public String getDescription() {
        return description.getDescription();
    }

    public CourseDescription description() {
        return description;
    }

    public CourseState state() {
        return state;
    }

    protected CourseDuration getDuration() {
        return duration;
    }

    protected CourseCapacity getCapacity() {
        return capacity;
    }

    public Teacher headTeacher() {
        return headTeacher;
    }

    public void setHeadTeacher(Teacher headTeacher) {
        this.headTeacher = headTeacher;
    }

    public void setDescription(String description) {
        this.description = new CourseDescription(description);
    }

    public boolean setCapacity(int minCacapity, int maxCapacity) {
        try {
            capacity = new CourseCapacity(minCacapity, maxCapacity);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean setDuration(Date startDate, Date endDate) {
        return this.duration.setIntervalDate(startDate, endDate);
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
                "\nname: " + name.getName() +
                "\ndescription: " + description.getDescription() +
                "\nduration: " + duration.getStartDate() + " - " + duration.getEndDate();
    }

}
