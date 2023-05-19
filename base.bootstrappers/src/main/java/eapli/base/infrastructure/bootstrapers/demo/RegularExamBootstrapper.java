package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.course.domain.Course;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamDate;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamSpecification;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.framework.actions.Action;
import eapli.base.exam.repositories.RegularExamRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegularExamBootstrapper implements Action{

    @Override
    public boolean execute() {
        CourseRepository courseRepository = PersistenceContext.repositories().courses();
        var course = courseRepository.findAll();

        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String grammar = Files.readString(Path.of("docs/us_2001/grammar_test_example.txt"));
            var specification = new RegularExamSpecification(grammar);

            for (Course c : course) {
                String name = c.courseName().getName();
                switch (name) {
                    case "Fisica":
                        var openDate1 = df.parse("01/01/2025");
                        var closeDate1 = df.parse("02/01/2025");
                        var date1 = new RegularExamDate(openDate1, closeDate1);
                        saveExam(specification, date1, c);
                        break;
                    case "Quimica":
                        var openDate2 = df.parse("15/01/2025");
                        var closeDate2 = df.parse("17/01/2025");
                        var date2 = new RegularExamDate(openDate2, closeDate2);
                        saveExam(specification, date2, c);
                        break;
                    case "Matematica":
                        var openDate3 = df.parse("20/01/2025");
                        var closeDate3 = df.parse("22/01/2025");
                        var date3 = new RegularExamDate(openDate3, closeDate3);
                        saveExam(specification, date3, c);
                        break;
                }
            }

            return true;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveExam(RegularExamSpecification specification, RegularExamDate date, Course course) {
        RegularExamRepository repo = PersistenceContext.repositories().regularExams();
        RegularExam regularExam = new RegularExam(specification, date, course);

        repo.save(regularExam);
    }
}
