package eapli.base.clientusermanagement.domain.users;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

@Embeddable
public class ShortName implements ValueObject, Comparable<ShortName> {
    private static final long serialVersionUID = 1L;
    private final String name;

    // TODO: use regular expressions to verify short name
    public ShortName(String shortName) {
        Preconditions.nonEmpty(shortName,
                "Short name should neither be null nor empty");

        this.name = shortName;
    }

    // for ORM
    protected ShortName() {
        name = null;
    }

    public static ShortName valueOf(final String shortName) {
        return new ShortName(shortName);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(final ShortName o) {
        return name.compareTo(o.name);
    }
}
