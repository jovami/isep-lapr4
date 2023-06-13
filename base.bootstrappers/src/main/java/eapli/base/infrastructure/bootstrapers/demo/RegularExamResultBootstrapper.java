package eapli.base.infrastructure.bootstrapers.demo;

import static eapli.base.examresult.domain.ExamGradeProperties.AFTER_CLOSING;
import static eapli.base.examresult.domain.ExamGradeProperties.NONE;
import static eapli.base.examresult.domain.ExamGradeProperties.ON_SUBMISSION;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.course.domain.CourseID;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.exam.domain.RegularExam;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.examresult.domain.*;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Username;

public class RegularExamResultBootstrapper implements Action {

    @Override
    public boolean execute() {
        RegularExamRepository regularExamRepo = PersistenceContext.repositories().regularExams();
        CourseRepository courseRepository = PersistenceContext.repositories().courses();
        StudentRepository studentRepo = PersistenceContext.repositories().students();

        var student1 = studentRepo.findByUsername(Username.valueOf("mary")).orElseThrow();
        var course = courseRepository.ofIdentity(CourseID.valueOf("Fisica-1")).orElseThrow();

        // TODO: we HAVE to find a way to identify an exam
        var regularExams = regularExamRepo.findByCourse(course).iterator();

        saveExamResult(student1, regularExams.next(), 10, 20, NONE);
        saveExamResult(student1, regularExams.next(), 13, 20, AFTER_CLOSING);
        saveExamResult(student1, regularExams.next(), 17, 20, ON_SUBMISSION);
        saveExamResult(student1, regularExams.next(), 19, 20, AFTER_CLOSING);

        return true;
    }

    public void saveExamResult(Student student, RegularExam exam, float grade, float maxGrade,
            ExamGradeProperties gradeProp) {
        var repo = PersistenceContext.repositories().examResults();
        var examResult = new RegularExamResult(student, exam, ExamGrade.valueOf(grade, maxGrade), gradeProp);
        repo.save(examResult);
    }
}
