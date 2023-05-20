package eapli.base.clientusermanagement.domain.users;

import java.util.Arrays;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

@Embeddable
public class FullName implements ValueObject {
    private static final long serialVersionUID = 1L;
    private final String[] names;

    public FullName(String... names) {
        Preconditions.nonNull(names, "Full name should not be null");
        Preconditions.ensure(names.length > 0, "Full name should not be empty");

        Invariants.ensure(Arrays.stream(names).allMatch(n -> n.matches("[[A-Za-z]+' '?]+")),
                "Full name contains invalid characters");

        this.names = names;
    }

    // for ORM
    public FullName() {
        names = null;
    }

    public static FullName valueOf(String... names) {
        return new FullName(names);
    }

    @Override
    public String toString() {
        return String.join(" ", names);
    }
}
