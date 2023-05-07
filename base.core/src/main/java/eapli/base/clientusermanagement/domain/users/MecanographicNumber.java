package eapli.base.clientusermanagement.domain.users;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class MecanographicNumber implements ValueObject, Comparable<MecanographicNumber> {
    private static final long serialVersionUID = 1L;
    private final String number;

    // TODO: use regular expressions to verify short name
    protected MecanographicNumber(String mecanographicNumber) {
        Preconditions.nonEmpty(mecanographicNumber,
                "Mecanographic number should neither be null nor empty");

        this.number = mecanographicNumber;
    }

    // for ORM
    protected MecanographicNumber() {
        number = null;
    }

    public static MecanographicNumber valueOf(final String mecanographicNumber) {
        return new MecanographicNumber(mecanographicNumber);
    }

    @Override
    public String toString() {
        return number;
    }

    @Override
    public int compareTo(final MecanographicNumber o) {
        return number.compareTo(o.number);
    }
}
