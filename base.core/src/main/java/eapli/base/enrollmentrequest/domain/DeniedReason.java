package eapli.base.enrollmentrequest.domain;

import java.util.Objects;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;

@Embeddable
public class DeniedReason implements ValueObject {

    public String description;

    protected DeniedReason() {
        description = null;
    }

    protected DeniedReason(String description) {
        this.description = description;
    }

    public void specifyDeniedReason(String description) {
        this.description = description;
    }

    public String obtainDenyingReason() {
        return this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeniedReason that = (DeniedReason) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
