package eapli.base.course.domain;

import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="COURSE")
public class Course implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IDCOURSE")
    private int code;

    @Column(name="COURSENAME",nullable = false,unique = true)
    private CourseName name;
    @Column(name="COURSEDESCRIPTION")
    private CourseDescription description;
    @Column(name="COURSESTATE",nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseState state;
    @Column(name="COURSEDURATION")
    private CourseDuration duration;
    @Column(name="COURSECAPACITY")
    private CourseCapacity capacity;


    //TODO: HEAD-TEACHER
    //@Column(name="HEADTEACHER",nullable = false)
    //private Teacher headTeacher;

    //TODO: STAFF(implementation)
    //@OneToOne(fetch = FetchType.LAZY)
    //private Staff staff;

    //TODO: ENROLLMENT

    //TODO: FORMATIVE/REGULAR EXAMS

    //TODO: LECTURE--COURSE MUST BE REFERENCED IN THE LECTURE

    //JPA needs empty constructor
    public Course(){
        this.state = CourseState.CLOSE;
        this.description = new CourseDescription();
        this.capacity = new CourseCapacity();
        this.duration = new CourseDuration();
    }
/*
    public  Course(String name,String description){
        this.name = new CourseName(name);
        createdCourse();
        this.description = new CourseDescription(description);
        this.capacity = new CourseCapacity();
        this.duration = new CourseDuration();
    }
*/
    public void setName(String name) {
        this.name = new CourseName(name);
    }

    public void closeCourse() {
        this.state = CourseState.CLOSED;
    }
    public void openEnrollments() {
        this.state = CourseState.ENROLL;
    }
    public void openCourse() {
        this.state = CourseState.OPEN;
    }
    public void closeEnrollments() {
        this.state = CourseState.INPROGRESS;
    }
    public void createdCourse() {
        this.state = CourseState.CLOSE;
    }

    /*    public void setHeadTeacher(Teacher headTeacher){
            this.headTeacher=headTeacher;
    }*/

    public String getName() {
        return name.getName();
    }

    protected String  getDescription() {
        return description.getDescription();
    }

    protected CourseState getState() {
        return state;
    }

    protected CourseDuration getDuration() {
        return duration;
    }
    protected CourseCapacity getCapacity() {
        return capacity;
    }

    public  void setDescription(String description) {
        this.description = new CourseDescription(description);
    }

    public boolean setCapacity(int minCacapity,int maxCapacity) {
        return capacity.setCapacities(minCacapity,maxCapacity);
    }

    public boolean setDuration(Date startDate, Date endDate) {
        return this.duration.setIntervalDate(startDate,endDate);
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Course)) {
            return false;}

        final Course o = (Course) other;
        if (this == o) {
            return true;}
        return this.name.equals(o.name) && this.code==o.code;}

    //TODO:TEST
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
        return "Course:"+
                "\ncode=" + code +
                "\nname=" + name +
                "\ndescription=" + description +
                "\nstate=" + state +
                "\nduration=" + duration +
                "\ncapacity=" + capacity;
    }

}