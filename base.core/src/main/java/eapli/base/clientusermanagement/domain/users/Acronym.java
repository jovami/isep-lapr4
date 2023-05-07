package eapli.base.clientusermanagement.domain.users;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class Acronym implements ValueObject, Comparable<Acronym> {
    private static final long serialVersionUID = 1L;
    private final String acronym;

    protected Acronym(String acronym) {
        Preconditions.nonEmpty(acronym, "Acronym should not be empty or null");

        //TODO: this is just a sample regex, discuss this later
        Invariants.ensure(acronym.matches("[A-Z]{2,10}"), "Invalid acronym format");

        this.acronym = acronym;
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
    public int compareTo(final Acronym o) {
        return acronym.compareTo(o.acronym);
    }
}
