package eapli.base.clientusermanagement.domain.users;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

@Embeddable
public class TaxPayerNumber implements ValueObject, Comparable<TaxPayerNumber> {
    private static final long serialVersionUID = 1L;
    private final String number;

    public TaxPayerNumber(String number) {
        Preconditions.nonEmpty(number, "Taxpayer number should neither be null nor empty");
        Invariants.ensure(number.matches("\\d{9}"), "Taxpayer number should be a 9-digit number");

        this.number = number;
    }

    // for ORM
    protected TaxPayerNumber() {
        number = null;
    }

    public static TaxPayerNumber valueOf(String number) {
        return new TaxPayerNumber(number);
    }

    @Override
    public String toString() {
        return number;
    }

    @Override
    public int compareTo(TaxPayerNumber o) {
        return this.number.compareTo(o.number);
    }
}
