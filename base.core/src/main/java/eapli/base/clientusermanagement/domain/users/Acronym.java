package eapli.base.clientusermanagement.domain.users;

import java.util.Objects;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

@Embeddable
public class Acronym implements ValueObject, Comparable<Acronym> {
    private static final long serialVersionUID = 1L;
    private final String acronym;

    public Acronym(String acronym) {
        Preconditions.nonEmpty(acronym, "Acronym should not be empty or null");
        Invariants.ensure(acronym.matches("[A-Z]+"), "Invalid acronym format");

        this.acronym = acronym;
    }

    // for ORM
    protected Acronym() {
        acronym = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Acronym acronym1 = (Acronym) o;
        return Objects.equals(acronym, acronym1.acronym);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acronym);
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
