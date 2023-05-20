package eapli.base.course.domain;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;

@Embeddable
public class CourseCapacity implements ValueObject {
    // TODO:Optional
    private final int NOCAPACITY = -1;
    private int maxStudentsEnrolled;
    private int minStudentsEnrolled;

    // values with -1 means that there are no min/max capacity defined
    public CourseCapacity() {
        maxStudentsEnrolled = NOCAPACITY;
        minStudentsEnrolled = NOCAPACITY;
    }

    public CourseCapacity(int minStudentsEnrolled, int maxStudentsEnrolled) {
        if (!setCapacities(minStudentsEnrolled, maxStudentsEnrolled)) {
            throw new IllegalArgumentException();
        }

    }

    protected boolean setCapacities(int newMinStudentsEnrolled, int newMaxStudentsEnrolled) {
        if ((newMaxStudentsEnrolled > newMinStudentsEnrolled) && (newMaxStudentsEnrolled > 1)
                && (newMinStudentsEnrolled >= 0)) {
            this.maxStudentsEnrolled = newMaxStudentsEnrolled;
            this.minStudentsEnrolled = newMinStudentsEnrolled;
            return true;
        }
        return false;
    }
    // TODO: needed?
    /*
     * private boolean addMaxCapacity(int newMaxStudentsEnrolled){
     * if(this.minStudentsEnrolled < newMaxStudentsEnrolled){
     * this.maxStudentsEnrolled = newMaxStudentsEnrolled;
     * return true;
     * }
     * return false;
     * }
     */
    /*
     * private boolean addMinCapacity(int newMinStudentsEnrolled){
     * if(this.maxStudentsEnrolled > newMinStudentsEnrolled){
     * this.minStudentsEnrolled = newMinStudentsEnrolled;
     * return true;
     * }
     * return false;
     * }
     */

    @Override
    public String toString() {
        return "Min students enrolled: " + minStudentsEnrolled + "\nMax students enrolled: " + maxStudentsEnrolled;
    }

    public int maxStudentsEnrolled() {
        return maxStudentsEnrolled;
    }

    public int minStudentsEnrolled() {
        return minStudentsEnrolled;
    }

}
