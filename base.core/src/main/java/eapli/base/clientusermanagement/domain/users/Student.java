package eapli.base.clientusermanagement.domain.users;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;

@Entity
public class Student implements AggregateRoot<MecanographicNumber> {
    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    //TODO: check one to one
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private MecanographicNumber mecanographicNumber;

    @Embedded
    private FullName fullName;

    @Embedded
    private ShortName shortName;

    @Embedded
    private DateOfBirth dateOfBirth;

    @Embedded
    private TaxPayerNumber taxPayerNumber;

    @OneToOne(optional = false)
    private SystemUser systemUser;

    public Student(final SystemUser user, final MecanographicNumber mecanographicNumber,
                   final FullName fullName, final ShortName shortName, final DateOfBirth dateOfBirth,
                   final TaxPayerNumber taxPayerNumber) {
        Preconditions.noneNull(user, mecanographicNumber, fullName, shortName, dateOfBirth, taxPayerNumber);

        this.systemUser = user;
        this.mecanographicNumber = mecanographicNumber;
        this.fullName = fullName;
        this.shortName = shortName;
        this.dateOfBirth = dateOfBirth;
        this.taxPayerNumber = taxPayerNumber;
    }

    protected Student() {
        // for ORM
    }

    public SystemUser user() {
        return systemUser;
    }

    public FullName fullName() {
        return fullName;
    }

    public ShortName shortName() {
        return shortName;
    }

    public DateOfBirth dateOfBirth() {
        return dateOfBirth;
    }

    public TaxPayerNumber taxPayerNumber() {
        return taxPayerNumber;
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof Student)) {
            return false;
        }

        final Student that = (Student) other;
        if (this == that) {
            return true;
        }
        return mecanographicNumber.equals(that.mecanographicNumber) && systemUser.sameAs(that.systemUser);
    }

    @Override
    public int compareTo(MecanographicNumber other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public String toString() {
        return "Student" +
                "\nMecanographic Number: " + mecanographicNumber +
                "\nFull Name: " + fullName +
                "\nShort Name: " + shortName +
                "\nDate Of Birth: " + dateOfBirth +
                "\nTax Payer Number: " + taxPayerNumber ;
    }

    public MecanographicNumber mecanographicNumber() {
        return identity();
    }

    @Override
    public MecanographicNumber identity() {
        return mecanographicNumber;
    }

    @Override
    public boolean hasIdentity(MecanographicNumber id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
