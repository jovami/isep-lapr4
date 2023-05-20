/*
 * Copyright (c) 2013-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.base.clientusermanagement.usermanagement.domain;

import eapli.base.clientusermanagement.domain.users.DateOfBirth;
import eapli.base.clientusermanagement.domain.users.FullName;
import eapli.base.clientusermanagement.domain.users.Manager;
import eapli.base.clientusermanagement.domain.users.ShortName;
import eapli.base.clientusermanagement.domain.users.TaxPayerNumber;
import eapli.framework.domain.model.DomainFactory;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

/**
 * A factory for User entities.
 *
 * This class demonstrates the use of the factory (DDD) pattern using a fluent
 * interface. it acts as a Builder (GoF).
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
public class ManagerBuilder implements DomainFactory<Manager> {

    private SystemUser systemUser;
    private FullName fullName;
    private ShortName shortName;
    private DateOfBirth dateOfBirth;
    private TaxPayerNumber taxPayerNumber;

    public ManagerBuilder withSystemUser(final SystemUser systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    public ManagerBuilder withFullName(final String fullName) {
        this.fullName = new FullName(fullName);
        return this;
    }

    public ManagerBuilder withShortName(final String shortName) {
        this.shortName = new ShortName(shortName);
        return this;
    }

    public ManagerBuilder withDateOfBirth(final String dateOfBirth) {
        // TODO create console.readLocalDate
        this.dateOfBirth = DateOfBirth.valueOf(dateOfBirth);
        return this;
    }

    public ManagerBuilder withTaxPayerNumber(final String taxPayerNumber) {
        this.taxPayerNumber = new TaxPayerNumber(taxPayerNumber);
        return this;
    }

    @Override
    public Manager build() {
        // since the factory knows that all the parts are needed it could throw
        // an exception. however, we will leave that to the constructor
        return new Manager(this.systemUser, this.fullName, this.shortName, this.dateOfBirth, this.taxPayerNumber);
    }
}
