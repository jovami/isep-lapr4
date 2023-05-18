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
package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.clientusermanagement.repositories.ManagerRepository;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.base.event.Meeting.repositories.MeetingParticipantRepository;
import eapli.base.event.Meeting.repositories.MeetingRepository;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.base.event.timetable.repositories.TimeTableRepository;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.board.repositories.BoardRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.jpa.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.exam.domain.question.QuestionRepository;
import eapli.base.formativeexam.repositories.FormativeExamRepository;
import eapli.base.formativeexam.repositories.FormativeExamSpecificationRepository;

/**
 *
 * @author nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory {

	@Override
	public UserRepository users(final TransactionalContext autoTx) {
		return new JpaAutoTxUserRepository(autoTx);
	}

	@Override
	public UserRepository users() {
		return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
				Application.settings().getExtendedPersistenceProperties());
	}

	@Override
	public JpaClientUserRepository clientUsers(final TransactionalContext autoTx) {
		return new JpaClientUserRepository(autoTx);
	}

	@Override
	public JpaClientUserRepository clientUsers() {
		return new JpaClientUserRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public SignupRequestRepository signupRequests(final TransactionalContext autoTx) {
		return new JpaSignupRequestRepository(autoTx);
	}

	@Override
	public SignupRequestRepository signupRequests() {
		return new JpaSignupRequestRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public BoardRepository boards(TransactionalContext autoTx) {
		return null;
	}

	@Override
	public BoardRepository boards() {
		return new JpaBoardRepository(Application.settings().getPersistenceUnitName());
	}

    @Override
    public StudentRepository students() {
        return new JpaStudentRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public TeacherRepository teachers() {
        return new JpaTeacherRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public ManagerRepository managers() {
        return new JpaManagerRepository(Application.settings().getPersistenceUnitName());
    }

	@Override
	public CourseRepository courses() {
		return new JpaCourseRepository(Application.settings().getPersistenceUnitName());
	}

	public RegularExamRepository exams() {
		return new JpaRegularExamRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public TransactionalContext newTransactionalContext() {
		return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
				Application.settings().getExtendedPersistenceProperties());
	}
	@Override
	public RecurringPatternRepository recurringPatterns(){
		return new JpaRecurringPatternRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public TimeTableRepository timeTables() {
		return new JpaTimeTableRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public StaffRepository staffs() {
		return new JpaStaffRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public MeetingRepository meetings() {
		return new JpaMeetingRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public MeetingParticipantRepository meetingParticipants() {
		return new JpaMeetingParticipantRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
    public QuestionRepository questions() {
		return new JpaQuestionRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public FormativeExamRepository formativeExams() {
		return new JpaFormativeExamRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public FormativeExamSpecificationRepository formativeExamSpecifications() {
		return new JpaFormativeExamSpecificationRepository(Application.settings().getPersistenceUnitName());
    }

	@Override
	public EnrollmentRequestRepository enrollmentRequests() {
		return new JpaEnrollmentRequestsRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public EnrollmentRepository enrollments() {
		return new JpaEnrollmentRepository(Application.settings().getPersistenceUnitName());
	}
}
