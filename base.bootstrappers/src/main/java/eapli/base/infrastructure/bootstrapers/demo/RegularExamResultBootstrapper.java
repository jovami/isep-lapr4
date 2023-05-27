package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.course.domain.CourseID;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.exam.domain.regular_exam.RegularExam;
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

        var regularExams = regularExamRepo.findByCourse(course).iterator();

        //TODO: we HAVE to find a way to identify an exam
        saveExamResult(student1, regularExams.next(), 10, "You need to study more...",
                ExamGradeProperties.NONE, ExamFeedbackProperties.NONE);

        saveExamResult(student1, regularExams.next(), 13, "You need to study a little more",
                ExamGradeProperties.AFTERCLOSING, ExamFeedbackProperties.AFTERCLOSING);

        saveExamResult(student1, regularExams.next(), 17, "Good Job",
                ExamGradeProperties.ONSUBMISSION, ExamFeedbackProperties.ONSUBMISSION);

        saveExamResult(student1, regularExams.next(), 19, "Perfect!",
                ExamGradeProperties.AFTERCLOSING, ExamFeedbackProperties.AFTERCLOSING);

        return true;
    }


    public void saveExamResult(Student student, RegularExam exam, float grade, String feedback,
                               ExamGradeProperties gradeProp, ExamFeedbackProperties feedbackProp) {
        var repo = PersistenceContext.repositories().examResults();
        var examResult = new RegularExamResult(student, exam, ExamGrade.valueOf(grade)
                , ExamFeedback.valueOf(feedback), gradeProp, feedbackProp);
        repo.save(examResult);
    }
}
