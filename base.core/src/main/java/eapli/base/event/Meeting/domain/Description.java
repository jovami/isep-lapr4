package eapli.base.event.meeting.domain;

import java.util.Objects;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;

@Embeddable
public class Description implements ValueObject {
    private String description;

    public Description() {

    }

    public Description(String description) {
        this.description = description;
    }

    public static Description valueOf(String description) {
        return new Description(description);
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Description that = (Description) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
