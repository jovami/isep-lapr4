/*
 * Copyright (c) 2013-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.base.enrollmentrequest.application.eventhandlers;

import java.util.Optional;

import eapli.base.clientusermanagement.repositories.ClientUserRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;

/**
 * The transactional controller for the use case "accept/refuse a signup
 * request".
 * <p>
 * following the guideline that a controller should only change one Aggregate,
 * we shouldn't be changing all these entities here, but should instead use
 * asynchronous events. However in this case we will take advantage of
 * TransactionalContext
 *
 * @todo handle the scenario where in the meantime the username is already used
 *       by some other user
 *
 * @author AJS on 08/04/2016.
 */
@UseCaseController
public class AcceptRefuseEnrollmentRequestControllerTxImpl
        implements AcceptRefuseEnrollmentRequestController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserManagementService userService = AuthzRegistry.userService();

    private final TransactionalContext txCtx = PersistenceContext.repositories()
            .newTransactionalContext();
    private final ClientUserRepository clientUserRepository = PersistenceContext
            .repositories().clientUsers(txCtx);
    private final EnrollmentRequestRepository enrollmentRequestRepository = PersistenceContext
            .repositories().enrollmentRequests(txCtx);
    private final EnrollmentRepository enrollmentRepository = PersistenceContext
            .repositories().enrollments(txCtx);

    /*
     * (non-Javadoc)
     *
     * @see eapli.base.clientusermanagement.application.
     * AcceptRefuseCourseApplicationController#acceptCourseApplication(eapli.base.
     * clientusermanagement.domain.CourseApplication)
     */
    @Override
    public EnrollmentRequest acceptCourseApplication(EnrollmentRequest theCourseApplication) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MANAGER);

        if (theCourseApplication == null) {
            throw new IllegalArgumentException();
        }
        Enrollment enrollment;

        // explicitly begin a transaction
        txCtx.beginTransaction();

        enrollment = new Enrollment(theCourseApplication.course(), theCourseApplication.student());
        enrollmentRepository.save(enrollment);
        theCourseApplication.approveEnrollmentRequest();
        theCourseApplication = enrollmentRequestRepository.save(theCourseApplication);

        // explicitly commit the transaction
        txCtx.commit();
        System.out.println(enrollmentRepository.findAll());

        return theCourseApplication;
    }

    /*
     * private EnrollmentRequest acceptTheCourseApplication(final EnrollmentRequest
     * theCourseApplication) {
     * theCourseApplication.approveEnrollmentRequest();
     * return this.enrollmentRequestRepository.save(theCourseApplication);
     * }
     */

    /*
     * (non-Javadoc)
     *
     * @see eapli.base.clientusermanagement.application.
     * AcceptRefuseCourseApplicationController#refuseCourseApplication(eapli.base.
     * clientusermanagement.domain.CourseApplication)
     */
    @Override
    public EnrollmentRequest refuseCourseApplication(EnrollmentRequest theCourseApplication, String denialReason) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MANAGER);

        if (theCourseApplication == null) {
            throw new IllegalArgumentException();
        }

        // explicitly begin a transaction
        txCtx.beginTransaction();

        theCourseApplication.denyEnrollmentRequest(denialReason);
        theCourseApplication = enrollmentRequestRepository.save(theCourseApplication);

        // explicitly commit the transaction
        txCtx.commit();

        return theCourseApplication;
    }

    @Override
    public Iterable<EnrollmentRequest> listPendingEnrollmentRequests() {
        return enrollmentRequestRepository.pendingEnrollmentRequests();
    }

    @Override
    public Optional<EnrollmentRequest> findFirstPendingEnrollmentRequest() {
        return enrollmentRequestRepository.findFirstPendingEnrollmentRequest();
    }

}
