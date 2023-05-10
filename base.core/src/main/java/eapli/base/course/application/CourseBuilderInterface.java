package eapli.base.course.application;

import java.util.Date;

public interface CourseBuilderInterface {
    void setName(String name);
    void setDescription(String description);
    boolean setCapacity(int minCapacity, int maxCapacity);
    boolean setDuration(Date startDate, Date endDate);
}
