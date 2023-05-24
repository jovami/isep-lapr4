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

import org.springframework.transaction.annotation.Transactional;

import eapli.base.enrollmentrequest.domain.events.EnrollmentRequestAcceptedEvent;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import eapli.framework.validations.Preconditions;

/**
 * the controller for the use case "Accept or refuse signup request"
 *
 * this implementation makes use of domain events to (1) follow the rule that
 * one controller should only modify one aggregate, and (2) notify other parts
 * of the system to react accordingly. For an alternative transactional approach
 * see {@link AcceptRefuseEnrollmentRequestControllerTxImpl}
 *
 * @author Paulo Gandra de Sousa
 */
@UseCaseController
public class AcceptRefuseEnrollmentRequestControllerEventfullImpl implements AcceptRefuseEnrollmentRequestController {

    private final EnrollmentRequestRepository enrollmentRequestRepository = PersistenceContext.repositories()
            .enrollmentRequests();
    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();
    private final EventPublisher dispatcher = InProcessPubSub.publisher();
    private final EnrollmentRepository enrollmentRepository = PersistenceContext.repositories().enrollments();

    @Override
    @SuppressWarnings("squid:S1226")
    public EnrollmentRequest acceptCourseApplication(EnrollmentRequest theCourseApplication) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MANAGER);

        Preconditions.nonNull(theCourseApplication);

        theCourseApplication = markCourseApplicationAsAccepted(theCourseApplication);
        return theCourseApplication;
    }

    /**
     * modify Signup Request to accepted
     *
     * @param theCourseApplication
     * @return
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     */
    @SuppressWarnings("squid:S1226")
    private EnrollmentRequest markCourseApplicationAsAccepted(EnrollmentRequest theCourseApplication) {
        Enrollment enrollment;
        // do just what is needed in the scope of this use case
        // theCourseApplication.approveEnrollmentRequest();

        enrollment = new Enrollment(theCourseApplication.course(), theCourseApplication.student());
        enrollmentRepository.save(enrollment);
        theCourseApplication.approveEnrollmentRequest();
        theCourseApplication = enrollmentRequestRepository.save(theCourseApplication);

        // notify interested parties (if any)
        final DomainEvent event = new EnrollmentRequestAcceptedEvent(theCourseApplication);
        dispatcher.publish(event);
        System.out.println(enrollmentRepository.findAll());

        return theCourseApplication;
    }

    @Override
    @Transactional
    public EnrollmentRequest refuseCourseApplication(final EnrollmentRequest theCourseApplication,
            final String deniedReason) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MANAGER);

        Preconditions.nonNull(theCourseApplication);

        theCourseApplication.denyEnrollmentRequest(deniedReason);
        return enrollmentRequestRepository.save(theCourseApplication);
    }

    /**
     *
     * @return
     */
    @Override
    public Iterable<EnrollmentRequest> listPendingEnrollmentRequests() {
        return enrollmentRequestRepository.pendingEnrollmentRequests();
    }

    @Override
    public Optional<EnrollmentRequest> findFirstPendingEnrollmentRequest() {
        return enrollmentRequestRepository.findFirstPendingEnrollmentRequest();
    }
}
