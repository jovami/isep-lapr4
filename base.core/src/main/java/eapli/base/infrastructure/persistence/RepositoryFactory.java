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
package eapli.base.infrastructure.persistence;

import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.clientusermanagement.repositories.*;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.base.event.lecture.repositories.LectureParticipantRepository;
import eapli.base.event.lecture.repositories.LectureRepository;
import eapli.base.event.meeting.repositories.MeetingParticipantRepository;
import eapli.base.event.meeting.repositories.MeetingRepository;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.base.event.timetable.repositories.TimeTableRepository;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.examresult.repository.RegularExamResultRepository;
import eapli.base.formativeexam.repositories.FormativeExamRepository;
import eapli.base.question.repositories.QuestionRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

/**
 * @author Paulo Gandra Sousa
 *
 */
public interface RepositoryFactory {

    /**
     * factory method to create a transactional context to use in the repositories
     *
     * @return
     */
    TransactionalContext newTransactionalContext();

    /**
     *
     * @param autoTx
     *               the transactional context to enrol
     * @return
     */
    UserRepository users(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    UserRepository users();

    /**
     *
     * @param autoTx
     *               the transactional context to enroll
     * @return
     */
    ClientUserRepository clientUsers(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    ClientUserRepository clientUsers();

    /**
     *
     * @param autoTx
     *               the transactional context to enroll
     * @return
     */
    SignupRequestRepository signupRequests(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    SignupRequestRepository signupRequests();

    /**
     *
     * @param autoTx
     *               the transactional context to enroll
     * @return
     */
    EnrollmentRequestRepository enrollmentRequests(TransactionalContext autoTx);

    /**
     *
     * @param autoTx
     *               the transactional context to enroll
     * @return
     */
    EnrollmentRepository enrollments(TransactionalContext autoTx);

    /**
     *
     * @param autoTx
     *               the transactional context to enroll
     * @return
     */
    BoardRepository boards(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    BoardRepository boards();

    BoardParticipantRepository boardParticipants();

    CourseRepository courses();

    StudentRepository students();

    TeacherRepository teachers();

    ManagerRepository managers();

    QuestionRepository questions();

    RegularExamRepository regularExams();

    FormativeExamRepository formativeExams();

    EnrollmentRequestRepository enrollmentRequests();

    EnrollmentRepository enrollments();

    RecurringPatternRepository recurringPatterns();

    TimeTableRepository timeTables();

    StaffRepository staffs();

    MeetingRepository meetings();

    MeetingRepository meetings(TransactionalContext autoTx);

    MeetingParticipantRepository meetingParticipants();

    LectureRepository lectures();

    LectureParticipantRepository lectureParticipants();

    RegularExamResultRepository examResults();

    BoardParticipantRepository boardParticipants(TransactionalContext txCtx);
}
