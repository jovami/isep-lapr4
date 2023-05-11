package eapli.base.course.application;


import eapli.base.course.domain.Course;


import java.util.Date;

public class CourseBuilder implements CourseBuilderInterface{

    private Course course;
    public CourseBuilder(){
        this.course = new Course();

    }

    @Override
    public void setName(String name) {
        course.setName(name);
    }

    @Override
    public void setDescription(String description) {
        course.setDescription(description);

    }

    @Override
    public boolean setCapacity(int minCapacity, int maxCapacity) {
        return course.setCapacity(minCapacity,maxCapacity);

    }

    @Override
    public boolean setDuration(Date startDate, Date endDate) {
        return course.setDuration(startDate,endDate);

    }

    public Course getCourse(){
        return this.course;
    }
}
