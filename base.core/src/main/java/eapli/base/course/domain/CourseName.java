package eapli.base.course.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.infrastructure.authz.domain.model.Username;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CourseName implements ValueObject {

    public String name;

    public CourseName(){
        name=null;
    }

    public static CourseName valueOf(final String courseName) {
        return new CourseName(courseName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseName that = (CourseName) o;
        return this.name.equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
    public CourseName(String name){
        setName(name);
    }

    protected void setName(String name){
        this.name=name;
    }
}
