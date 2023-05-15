package eapli.base.clientusermanagement.domain.users;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Acronym implements ValueObject, Comparable<Acronym> {
    private static final long serialVersionUID = 1L;
    private final String acronym;

    public Acronym(String acronym) {
        Preconditions.nonEmpty(acronym, "Acronym should not be empty or null");

        //TODO: this is just a sample regex, discuss this later
        Invariants.ensure(acronym.matches("[A-Z]{2,10}"), "Invalid acronym format");

        this.acronym = acronym;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Acronym acronym1 = (Acronym) o;
        return Objects.equals(acronym, acronym1.acronym);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acronym);
    }

    // for ORM
    protected Acronym() {
        acronym = null;
    }

    public static Acronym valueOf(final String acronym) {
        return new Acronym(acronym);
    }

    @Override
    public String toString() {
        return acronym;
    }

    @Override
    public int compareTo(Acronym o) {
        return acronym.compareTo(o.acronym);
    }
}
