package eapli.base.course.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class CourseDescription implements ValueObject {
    //TODO: verify max length??
    private final int MAXLENGTH = 100;

    public String getDescription() {
        return description;
    }

    private String description;
    public CourseDescription(){
        description=null;
    }
    public CourseDescription(String description){
        setDescription(description);
    }

    /*protected boolean checkDescription(String description){
        return description.length()<=MAXLENGTH;
    }*/

    @Override
    public String toString() {
        return description;
    }

    protected void setDescription(String description){
            this.description=description;
    }
}
