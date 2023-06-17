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
package eapli.base.persistence.impl.inmemory;

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
import eapli.base.formativeexam.repositories.FormativeExamRepository;
import eapli.base.infrastructure.bootstrapers.BaseBootstrapper;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.question.repositories.QuestionRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.inmemory.InMemoryUserRepository;

/**
 *
 * @author nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

    static {
        // only needed because of the in memory persistence
        new BaseBootstrapper().execute();
    }

    @Override
    public UserRepository users(final TransactionalContext tx) {
        return new InMemoryUserRepository();
    }

    @Override
    public UserRepository users() {
        return users(null);
    }

    @Override
    public ClientUserRepository clientUsers(final TransactionalContext tx) {

        return new InMemoryClientUserRepository();
    }

    @Override
    public ClientUserRepository clientUsers() {
        return clientUsers(null);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return signupRequests(null);
    }

    @Override
    public EnrollmentRequestRepository enrollmentRequests(TransactionalContext autoTx) {
        return new InMemoryEnrollmentRequestsRepository();
    }

    @Override
    public EnrollmentRepository enrollments(TransactionalContext autoTx) {
        return new InMemoryEnrollmentRepository();
    }

    @Override
    public CourseRepository courses() {
        return new InMemoryCourseRepository();
    }

    @Override
    public RegularExamRepository regularExams() {
        return new InMemoryRegularExamRepository();
    }

    @Override
    public SignupRequestRepository signupRequests(final TransactionalContext tx) {
        return new InMemorySignupRequestRepository();
    }

    @Override
    public BoardRepository boards() {
        return boards(null);
    }

    @Override
    public BoardRepository boards(final TransactionalContext tx) {
        return null; // new InMemoryBoardRepository();
    }

    @Override
    public TransactionalContext newTransactionalContext() {
        // in memory does not support transactions...
        return null;
    }

    @Override
    public BoardParticipantRepository boardParticipants(){return new InMemoryBoardParticipantRepository();}

    @Override
    public StudentRepository students() {
        return new InMemoryStudentRepository();
    }

    @Override
    public TeacherRepository teachers() {
        return new InMemoryTeacherRepository();
    }

    @Override
    public ManagerRepository managers() {
        return new InMemoryManagerRepository();
    }

    @Override
    public QuestionRepository questions() {
        return new InMemoryQuestionRepository();
    }

    @Override
    public FormativeExamRepository formativeExams() {
        return new InMemoryFormativeExamRepository();
    }

    @Override
    public EnrollmentRequestRepository enrollmentRequests() {
        return new InMemoryEnrollmentRequestsRepository();
    }

    @Override
    public EnrollmentRepository enrollments() {
        return new InMemoryEnrollmentRepository();
    }

    @Override
    public RecurringPatternRepository recurringPatterns() {
        return new InMemoryRecurringPatternRepository();
    }

    @Override
    public TimeTableRepository timeTables() {
        return new InMemoryTimeTableRepository();
    }

    @Override
    public StaffRepository staffs() {
        return new InMemoryStaffRepository();
    }

    @Override
    public MeetingRepository meetings() {
        return new InMemoryMeetingRepository();
    }

    @Override
    public MeetingRepository meetings(TransactionalContext autoTx) {
        return new InMemoryMeetingRepository();
    }

    @Override
    public MeetingParticipantRepository meetingParticipants() {
        return new InMemoryMeetingParticipantRepository();
    }

    @Override
    public LectureRepository lectures() {
        return new InMemoryLectureRepository();
    }

    @Override
    public LectureParticipantRepository lectureParticipants() {
        return new InMemoryLectureParticipantRepository();
    }

    @Override
    public InMemoryRegularExamResultRepository examResults() {
        return new InMemoryRegularExamResultRepository();
    }

    @Override
    public BoardParticipantRepository boardParticipants(TransactionalContext txCtx) {
        return new InMemoryBoardParticipantRepository();
    }
}
