package eapli.base.infrastructure.bootstrapers.demo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eapli.base.clientusermanagement.domain.users.Acronym;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseFactory;
import eapli.base.course.domain.CourseID;
import eapli.base.course.dto.CreateCourseDTO;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.domain.regular_exam.RegularExamDate;
import eapli.base.exam.domain.regular_exam.RegularExamSpecification;
import eapli.base.formativeexam.domain.FormativeExamFactory;
import eapli.base.infrastructure.grammar.GrammarContext;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.question.domain.QuestionFactory;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * ExamBootstrapper
 */
public class ExamBootstrapper implements Action {

    private final RepositoryFactory repos;

    private Teacher teacher;
    private Student student;

    private static final Logger logger;
    private static final Acronym ACRONYM;
    private static final String prefix = "examples.exams";

    static {
        ACRONYM = Acronym.valueOf("MAM");

        logger = LoggerFactory.getLogger(FormativeExamFactory.class);
    }

    public ExamBootstrapper() {
        super();

        this.repos = PersistenceContext.repositories();
    }

    @Override
    public boolean execute() {
        this.teacher = this.repos.teachers().ofIdentity(ACRONYM)
                .orElseThrow();
        this.student = this.repos.students().findByUsername(Username.valueOf("sairy"))
                .orElseThrow();

        var dir = new File(prefix);

        if (!dir.exists() && !dir.isDirectory()) {
            logger.error("File at '{}' is not a course directory", dir.getAbsolutePath());
            return false;
        }

        try {
            for (final var courseDir : dir.listFiles()) {
                bootstrapCourseExams(courseDir);
            }
            return true;
        } catch (IOException e) {
            logger.error("Failed to bootstrap exams", e);
            return false;
        }
    }

    private void bootstrapCourseExams(File path) throws IOException {
        var course = bootstrapCourse(path.getName());

        for (final var subfile : path.listFiles()) {
            /* questions */
            if (subfile.isDirectory()) {
                if (!subfile.getName().equalsIgnoreCase("questions")) {
                    logger.warn("Unexpected file found for course '{}': '{}'", course.identity(), subfile.getName());
                    continue;
                }
                for (final var question : subfile.listFiles()) {
                    bootstrapQuestion(question, course);
                }
            }

            switch (FilenameUtils.getExtension(subfile.getName())) {
                case "fexam":
                    bootstrapFormativeExam(subfile, course);
                    break;
                case "exam":
                    bootstrapRegularExam(subfile, course);
                    break;
            }
        }
    }

    private void bootstrapFormativeExam(File spec, Course c) throws IOException {
        var svc = GrammarContext.grammarTools().formativeExamValidator();

        logger.info("Bootstrapping formative exam '{}' for course '{}'", spec.getName(), c.identity());

        var fexam = new FormativeExamFactory(svc).build(c, spec).orElseThrow();

        this.repos.formativeExams().save(fexam);
    }

    private void bootstrapRegularExam(File spec, Course c) throws IOException {
        var svc = GrammarContext.grammarTools().regularExamValidator();

        logger.info("Bootstrapping exam '{}' for course '{}'", spec.getName(), c.identity());

        var now = LocalDateTime.now();
        var end = now.plusDays(3);

        var date = RegularExamDate.valueOf(now, end);

        if (!svc.validate(spec))
            throw new IllegalArgumentException();
        var exam = new RegularExam(RegularExamSpecification.valueOf(spec), date, c);

        this.repos.regularExams().save(exam);
    }

    private Course bootstrapCourse(String name) {
        var cName = CourseID.valueOf(name.toUpperCase());

        logger.info("Bootstrapping course: '{}'", cName);

        var df = DateTimeFormatter.ofPattern("d/M/yyyy");

        var desc = String.format("Bootstrap of %s", cName);
        var startDate = LocalDate.parse("20/05/2023", df);
        var endDate = LocalDate.parse("30/11/2023", df);

        var dto = new CreateCourseDTO(cName.title(), cName.code(), desc,
                startDate, endDate, 120, 420);

        var course = new CourseFactory().build(dto);
        course.open();
        course.setHeadTeacher(teacher);

        course.openEnrollments();

        course = this.repos.courses().save(course);

        var request = new EnrollmentRequest(course, student);
        request.approveEnrollmentRequest();

        this.repos.enrollmentRequests().save(request);

        var enrollment = new Enrollment(course, student);
        this.repos.enrollments().save(enrollment);

        course.closeEnrollments();
        return this.repos.courses().save(course);
    }

    private void bootstrapQuestion(File spec, Course c) throws IOException {
        var svc = GrammarContext.grammarTools().questionValidator();

        logger.info("Bootstrapping question '{}' for formative exams of course '{}'", spec.getName(), c.identity());

        var question = new QuestionFactory(svc).build(c, spec).orElseThrow();

        this.repos.questions().save(question);
    }

}
