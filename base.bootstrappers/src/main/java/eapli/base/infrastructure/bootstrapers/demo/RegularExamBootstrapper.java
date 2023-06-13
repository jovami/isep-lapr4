package eapli.base.infrastructure.bootstrapers.demo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import eapli.base.course.domain.Course;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.exam.domain.regular_exam.RegularExamFactory;
import eapli.base.exam.dto.resolution.ExamResolutionDTO;
import eapli.base.exam.dto.resolution.ExamResolutionDTO.Section;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.infrastructure.grammar.GrammarContext;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;

public class RegularExamBootstrapper implements Action {

    @Override
    public boolean execute() {
        CourseRepository courseRepository = PersistenceContext.repositories().courses();
        var course = courseRepository.findAll();

        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");
            var grammar = new File("test.exam");

            for (Course c : course) {
                switch (c.identity().title()) {
                    case "Fisica":
                        var openDate1 = LocalDateTime.parse("01/01/2022 13:20", df);
                        var closeDate1 = LocalDateTime.parse("02/01/2022 14:20", df);
                        saveExam(openDate1, closeDate1, c, grammar);
                        var openDate4 = LocalDateTime.parse("04/01/2021 13:20", df);
                        var closeDate4 = LocalDateTime.parse("05/01/2021 14:20", df);
                        saveExam(openDate4, closeDate4, c, grammar);
                        var openDate5 = LocalDateTime.parse("08/01/2020 13:20", df);
                        var closeDate5 = LocalDateTime.parse("09/01/2020 14:20", df);
                        saveExam(openDate5, closeDate5, c, grammar);
                        var openDate6 = LocalDateTime.parse("08/01/2020 13:20", df);
                        var closeDate6 = LocalDateTime.parse("09/01/2100 14:20", df);
                        saveExam(openDate6, closeDate6, c, grammar);
                        break;
                    case "Quimica":
                        var openDate2 = LocalDateTime.parse("15/01/2025 13:20", df);
                        var closeDate2 = LocalDateTime.parse("17/01/2025 14:20", df);
                        saveExam(openDate2, closeDate2, c, grammar);
                        break;
                    case "Matematica":
                        var openDate3 = LocalDateTime.parse("20/01/2025 13:20", df);
                        var closeDate3 = LocalDateTime.parse("22/01/2025 14:20", df);
                        saveExam(openDate3, closeDate3, c, grammar);
                        break;
                }
            }

            var openDate1 = LocalDateTime.parse("01/01/2022 13:20", df);
            var closeDate1 = LocalDateTime.parse("02/01/2022 14:20", df);

            var c = course.iterator().next();

            var exam = new RegularExamFactory(GrammarContext.grammarTools().regularExamValidator())
                    .build(openDate1, closeDate1, c, grammar).orElseThrow();

            var resolution = new ExamResolutionDTO(
                    List.of(new Section(
                            List.of("1-2\n2-1\n3-3\n4-4\n", "2", "1\n3")),
                            new Section(
                                    List.of("Avenida da Boavista", "2", "true", "fly\nbrave"))),
                    closeDate1.minusMinutes(30), exam.identity());

            var result = GrammarContext.grammarTools().examGrader().correctExam(exam, resolution);
            System.out.println(result);

            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveExam(LocalDateTime start, LocalDateTime end, Course course, File specification)
            throws IOException {
        RegularExamRepository repo = PersistenceContext.repositories().regularExams();
        var exam = new RegularExamFactory(GrammarContext.grammarTools().regularExamValidator()).build(start, end,
                course, specification);
        exam.ifPresent(repo::save);
    }
}
