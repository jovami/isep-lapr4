package eapli.base.clientusermanagement.domain.users;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;

@Entity
public class Teacher implements AggregateRoot<Acronym> {
    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    // TODO: check one to one
    @EmbeddedId
    private Acronym acronym;

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

    public Teacher(final SystemUser user, final Acronym acronym,
            final FullName fullName, final ShortName shortName, final DateOfBirth dateOfBirth,
            final TaxPayerNumber taxPayerNumber) {
        Preconditions.noneNull(user, acronym, fullName, shortName, dateOfBirth, taxPayerNumber);

        this.systemUser = user;
        this.acronym = acronym;
        this.fullName = fullName;
        this.shortName = shortName;
        this.dateOfBirth = dateOfBirth;
        this.taxPayerNumber = taxPayerNumber;
    }

    protected Teacher() {
        // for ORM
    }

    public SystemUser user() {
        return systemUser;
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
        if (!(other instanceof Teacher)) {
            return false;
        }

        final Teacher that = (Teacher) other;
        if (this == that) {
            return true;
        }
        return acronym.equals(that.acronym) && systemUser.sameAs(that.systemUser);
    }

    @Override
    public int compareTo(Acronym other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public String toString() {
        return "Teacher" +
                "\nAcronym: " + acronym +
                "\nFull Name: " + fullName +
                "\nShort Name: " + shortName +
                "\nDate Of Birth: " + dateOfBirth +
                "\nTax Payer Number: " + taxPayerNumber;
    }

    public Acronym acronym() {
        return identity();
    }

    @Override
    public Acronym identity() {
        return acronym;
    }

    @Override
    public boolean hasIdentity(Acronym id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
