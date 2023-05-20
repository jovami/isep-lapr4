package eapli.base.clientusermanagement.domain.users;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

@Embeddable
public class ManagerId implements ValueObject, Comparable<ManagerId> {
    private static final long serialVersionUID = 1L;
    private final String id;

    protected ManagerId(String id) {
        Preconditions.nonEmpty(id, "Manager ID should neither be null nor empty");

        // TODO: discuss the ID later
        Invariants.ensure(id.matches("[A-Za-z0-9]+"), "Manager ID contains invalid characters");

        this.id = id;
    }

    // for ORM
    public ManagerId() {
        id = null;
    }

    public static ManagerId valueOf(String id) {
        return new ManagerId(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int compareTo(ManagerId o) {
        return id.compareTo(o.id);
    }
}
