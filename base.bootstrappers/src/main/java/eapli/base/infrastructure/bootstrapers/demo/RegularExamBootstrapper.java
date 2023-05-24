package eapli.base.infrastructure.bootstrapers.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import eapli.base.course.domain.Course;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamDate;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamSpecification;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;

public class RegularExamBootstrapper implements Action {

    @Override
    public boolean execute() {
        CourseRepository courseRepository = PersistenceContext.repositories().courses();
        var course = courseRepository.findAll();

        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");
            String grammar = Files.readString(Path.of("docs/us_2001/grammar_test_example.txt"));
            var specification = new RegularExamSpecification(grammar);

            for (Course c : course) {
                switch (c.identity().title()) {
                    case "Fisica":
                        var openDate1 = LocalDateTime.parse("01/01/2025 13:20", df);
                        var closeDate1 = LocalDateTime.parse("02/01/2025 14:20", df);
                        var date1 = new RegularExamDate(openDate1, closeDate1);
                        saveExam(specification, date1, c);
                        break;
                    case "Quimica":
                        var openDate2 = LocalDateTime.parse("15/01/2025 13:20", df);
                        var closeDate2 = LocalDateTime.parse("17/01/2025 14:20", df);
                        var date2 = new RegularExamDate(openDate2, closeDate2);
                        saveExam(specification, date2, c);
                        break;
                    case "Matematica":
                        var openDate3 = LocalDateTime.parse("20/01/2025 13:20", df);
                        var closeDate3 = LocalDateTime.parse("22/01/2025 14:20", df);
                        var date3 = new RegularExamDate(openDate3, closeDate3);
                        saveExam(specification, date3, c);
                        break;
                }
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveExam(RegularExamSpecification specification, RegularExamDate date, Course course) {
        RegularExamRepository repo = PersistenceContext.repositories().regularExams();
        RegularExam regularExam = new RegularExam(specification, date, course);

        repo.save(regularExam);
    }
}
