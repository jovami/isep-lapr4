package eapli.base.infrastructure.bootstrapers.demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.io.FileUtils;

import eapli.base.course.domain.Course;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.domain.regular_exam.RegularExamDate;
import eapli.base.exam.domain.regular_exam.RegularExamSpecification;
import eapli.base.exam.dto.resolution.ExamResolutionDTO;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.infrastructure.grammar.GrammarContext;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;
import eapli.base.exam.dto.resolution.ExamResolutionDTO.Section;

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
                        var openDate1 = LocalDateTime.parse("01/01/2022 13:20", df);
                        var closeDate1 = LocalDateTime.parse("02/01/2022 14:20", df);
                        var date1 = new RegularExamDate(openDate1, closeDate1);
                        saveExam(specification, date1, c);
                        var openDate4 = LocalDateTime.parse("04/01/2021 13:20", df);
                        var closeDate4 = LocalDateTime.parse("05/01/2021 14:20", df);
                        var date4 = new RegularExamDate(openDate4, closeDate4);
                        saveExam(specification, date4, c);
                        var openDate5 = LocalDateTime.parse("08/01/2020 13:20", df);
                        var closeDate5 = LocalDateTime.parse("09/01/2020 14:20", df);
                        var date5 = new RegularExamDate(openDate5, closeDate5);
                        saveExam(specification, date5, c);
                        var openDate6 = LocalDateTime.parse("08/01/2020 13:20", df);
                        var closeDate6 = LocalDateTime.parse("09/01/2100 14:20", df);
                        var date6 = new RegularExamDate(openDate6, closeDate6);
                        saveExam(specification, date6, c);
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


            var resolution = new ExamResolutionDTO(
                    List.of(new Section(
                            List.of("1-2\n2-1\n3-3\n4-4\n"/*, "2", "1,3"*/)),
                        new Section(
                            List.of("Avenida da Boavista", "2", "true")
                            )));

            var openDate1 = LocalDateTime.parse("01/01/2022 13:20", df);
            var closeDate1 = LocalDateTime.parse("02/01/2022 14:20", df);
            var date1 = new RegularExamDate(openDate1, closeDate1);
            var c = course.iterator().next();

            var exam = new RegularExam(
                    RegularExamSpecification.valueOf(new File("test.exam")),
                    date1,
                    c);

            var result = GrammarContext.grammarTools().examGrader().correctExam(exam, resolution);
            System.out.println(result);


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
