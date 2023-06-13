package eapli.base.exam.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

@Embeddable
public class RegularExamSpecification implements ValueObject {

    @Column(columnDefinition = "CLOB")
    private final String spec;

    protected RegularExamSpecification() {
        this.spec = null;
    }

    protected RegularExamSpecification(String spec) {
        Preconditions.nonNull(spec, "Specification cannot be null");
        Preconditions.nonEmpty(spec, "Specification cannot be empty");

        this.spec = spec;
    }

    public String specification() {
        return this.spec;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || this.getClass() != obj.getClass())
            return false;

        var o = (RegularExamSpecification) obj;
        return this.spec.equals(o.spec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.spec);
    }

    @Override
    public String toString() {
        return String.format(this.spec);
    }

}
