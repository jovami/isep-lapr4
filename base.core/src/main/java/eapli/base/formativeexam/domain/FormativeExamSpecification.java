package eapli.base.formativeexam.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

/**
 * FormativeExamSpecification
 */
@Embeddable
public class FormativeExamSpecification implements ValueObject {

    @Column(columnDefinition = "CLOB") // store strings larger than VARCHAR(255)
    private final String spec;

    // for ORM
    protected FormativeExamSpecification() {
        this.spec = null;
    }

    /**
     * Creates a FormativeExamSpecification from a given string
     *
     * @param spec specification string
     *
     * @apiNote INTERNAL USAGE ONLY!!
     *          As the specification must follow a very specific grammar
     */
    protected FormativeExamSpecification(String spec) {
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

        var o = (FormativeExamSpecification) obj;
        return this.spec.equals(o.spec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.spec);
    }

    @Override
    public String toString() {
        return String.format("Specification:\n%s", this.spec);
    }
}
