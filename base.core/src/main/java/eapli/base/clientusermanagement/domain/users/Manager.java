package eapli.base.clientusermanagement.domain.users;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Manager implements AggregateRoot<ManagerId>, Serializable {
    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    //TODO: check one to one
    @EmbeddedId
    private ManagerId managerId;

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

    public Manager(final SystemUser user, final ManagerId managerId,
                   final FullName fullName, final ShortName shortName, final DateOfBirth dateOfBirth,
                   final TaxPayerNumber taxPayerNumber) {
        Preconditions.noneNull(user, managerId, fullName, shortName, dateOfBirth, taxPayerNumber);

        this.systemUser = user;
        this.managerId = managerId;
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
        return managerId.equals(that.managerId) && systemUser.sameAs(that.systemUser);
    }

    @Override
    public int compareTo(ManagerId other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerId=" + managerId +
                ", fullName=" + fullName +
                ", shortName=" + shortName +
                ", dateOfBirth=" + dateOfBirth +
                ", taxPayerNumber=" + taxPayerNumber +
                '}';
    }

    public ManagerId managerId() {
        return identity();
    }

    @Override
    public ManagerId identity() {
        return managerId;
    }

    @Override
    public boolean hasIdentity(ManagerId id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}