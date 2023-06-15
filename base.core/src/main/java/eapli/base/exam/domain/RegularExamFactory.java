package eapli.base.exam.domain;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import eapli.base.course.domain.Course;
import eapli.base.exam.application.parser.RegularExamValidatorService;

public class RegularExamFactory {
    private final RegularExamValidatorService svc;

    public RegularExamFactory(RegularExamValidatorService svc) {
        super();
        this.svc = svc;
    }

    /**
     * Attempts to create an Exam for a Course with a specification
     * read from a File
     *
     * @param c        course to create the Exam for
     * @param specFile file to read the specification from
     *
     * @return Exam if specification complies with the grammar
     *
     * @throws IOException if an error occurs when reading the file contents
     */
    public Optional<RegularExam> build(String title, LocalDateTime start, LocalDateTime end, Course c, File specFile) throws IOException {
        if (!this.svc.validate(specFile))
            return Optional.empty();

        var id = RegularExamTitle.valueOf(title);

        var date = RegularExamDate.valueOf(start, end);

        var spec = new RegularExamSpecification(readFileToString(specFile, StandardCharsets.UTF_8));

        return Optional.of(new RegularExam(id, spec, date, c));
    }

    /**
     * Attempts to update an Exam with a specification
     * @param exam      Exam to update
     * @param specFile  file to read the specification from
     * @return Exam if the specification complies with the grammar
     * @throws IOException if an error occurs when reading the file contents
     */
    public Optional<RegularExam> updateExamSpecification(RegularExam exam, File specFile) throws IOException {
        if (!this.svc.validate(specFile))
            return Optional.empty();

        var spec = new RegularExamSpecification(readFileToString(specFile, StandardCharsets.UTF_8));

        exam.updateSpecification(spec);
        return Optional.of(exam);
    }

    /**
     * Attempts to update an Exam with a specification provided in a list,
     * where each of the elements corresponds to a line
     * of a hypothetical File.
     *
     * @param exam      Exam to update
     * @param specLines list containing the specification lines
     *
     * @return Exam if specification complies with the grammar
     *
     * @apiNote The strings contained in the list need not be terminated
     *          by a new line
     */
    public Optional<RegularExam> updateExamSpecification(RegularExam exam, List<String> specLines) {
        var fullSpec = specLines.stream()
                .collect(Collectors.joining("\n"));

        if (!this.svc.validate(fullSpec))
            return Optional.empty();

        var spec = new RegularExamSpecification(fullSpec);

        exam.updateSpecification(spec);
        return Optional.of(exam);
    }

    /**
     * Attempts to create an Exam for a Course from a specification
     * provided in a list, where each of the elements corresponds to a line
     * of a hypothetical File.
     *
     * @param c         course to create the Exam for
     * @param specLines list containing the specification lines
     *
     * @return Exam if specification complies with the grammar
     *
     * @apiNote The strings contained in the list need not be terminated
     *          by a new line
     */
    public Optional<RegularExam> build(String title, LocalDateTime start, LocalDateTime end, Course c, List<String> specLines) {
        var fullSpec = specLines.stream()
                .collect(Collectors.joining("\n"));

        if (!this.svc.validate(fullSpec))
            return Optional.empty();

        var id = RegularExamTitle.valueOf(title);

        var date = RegularExamDate.valueOf(start, end);

        return Optional.of(new RegularExam(id, new RegularExamSpecification(fullSpec), date, c));
    }

}
