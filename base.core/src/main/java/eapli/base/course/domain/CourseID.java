package eapli.base.course.domain;

import java.util.Objects;
import java.util.regex.Pattern;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

/**
 * CourseID
 */
@Embeddable
public class CourseID implements ValueObject, Comparable<CourseID> {

    private final String id;
    // private final String title;
    // private final Long code;

    // for ORM
    protected CourseID() {
        // this.title = null;
        // this.code = null;
        this.id = null;
    }

    protected CourseID(String title, long code) {
        Preconditions.noneNull(title, code);
        Preconditions.nonEmpty(title, "Course title cannot be empty");
        Preconditions.nonNegative(code, "Course code cannot be negative");

        // this.title = title; //.toUpperCase();
        // this.code = code;
        this.id = String.format("%s-%d", title, code);
    }

    public static CourseID valueOf(String title, long code) {
        return new CourseID(title, code);
    }

    public static CourseID valueOf(String id) {
        var pat = Pattern.compile("^[a-zA-Z]+-[0-9]+$");
        Invariants.matches(pat, id, "Course id must be of the format TITLE-CODE");

        var components = id.split("-");
        return new CourseID(components[0], Long.parseLong(components[1]));
    }

    public String title() {
        // return this.title;
        return this.id.split("-")[0];
    }

    public Long code() {
        // return this.code;
        return Long.parseLong(this.id.split("-")[1]);
    }

    public String id() {
        // return this.toString();
        return this.id;
    }

    @Override
    public int compareTo(CourseID other) {
        return this.toString().compareTo(other.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || this.getClass() != obj.getClass())
            return false;
        var o = (CourseID) obj;
        // return this.code == o.code
        //         && this.title.equals(o.title);
        return this.id.equals(o.id);
    }

    @Override
    public int hashCode() {
        // return Objects.hash(this.title, this.code);
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        // return String.format("%s-%d", this.title, this.code);
        return this.id;
    }
}
