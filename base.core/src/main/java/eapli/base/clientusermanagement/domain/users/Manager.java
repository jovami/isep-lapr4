package eapli.base.clientusermanagement.domain.users;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;

@Entity
public class Manager implements AggregateRoot<Integer> {
    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    // TODO: check one to one
    // @EmbeddedId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int managerId;

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

    public Manager(final SystemUser user,
            final FullName fullName, final ShortName shortName, final DateOfBirth dateOfBirth,
            final TaxPayerNumber taxPayerNumber) {
        Preconditions.noneNull(user, fullName, shortName, dateOfBirth, taxPayerNumber);

        this.systemUser = user;
        this.fullName = fullName;
        this.shortName = shortName;
        this.dateOfBirth = dateOfBirth;
        this.taxPayerNumber = taxPayerNumber;
    }

    protected Manager() {
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
        if (!(other instanceof Manager)) {
            return false;
        }

        final Manager that = (Manager) other;
        if (this == that) {
            return true;
        }
        return managerId == (that.managerId) && systemUser.sameAs(that.systemUser);
    }

    @Override
    public int compareTo(Integer other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public String toString() {
        return "Manager" +
                "\nManager Id: " + managerId +
                "\nFull Name: " + fullName +
                "\nShort Name: " + shortName +
                "\nDate Of Birth: " + dateOfBirth +
                "\nTax Payer Number: " + taxPayerNumber;
    }

    @Override
    public Integer identity() {
        return managerId;
    }

    @Override
    public boolean hasIdentity(Integer id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
