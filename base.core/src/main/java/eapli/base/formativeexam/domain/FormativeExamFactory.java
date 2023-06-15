package eapli.base.formativeexam.domain;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import eapli.base.course.domain.Course;
import eapli.base.formativeexam.application.parser.FormativeExamValidatorService;

/**
 * FormativeExamFactory
 */
public final class FormativeExamFactory {

    private final FormativeExamValidatorService svc;

    public FormativeExamFactory(FormativeExamValidatorService svc) {
        super();
        this.svc = svc;
    }

    /**
     * Attempts to create a FormativeExam for a Course with a specification
     * read from a File
     *
     * @param c        course to create the FormativeExam for
     * @param specFile file to read the specification from
     *
     * @return FormativeExam if specification complies with the grammar
     *
     * @throws IOException if an error occurs when reading the file contents
     */
    public Optional<FormativeExam> build(String title, Course c, File specFile) throws IOException {
        if (!this.svc.validate(specFile))
            return Optional.empty();

        var id = FormativeExamTitle.valueOf(title);

        var spec = new FormativeExamSpecification(readFileToString(specFile, StandardCharsets.UTF_8));

        return Optional.of(new FormativeExam(id, c, spec));

    }

    /**
     * Attempts to create a FormativeExam for a Course from a specification
     * provided in a list, where each of the elements corresponds to a line
     * of a hypothetical File.
     *
     * @param c         course to create the FormativeExam for
     * @param specLines list containing the specification lines
     *
     * @return FormativeExam if specification complies with the grammar
     *
     * @apiNote The strings contained in the list need not be terminated
     *          by a new line
     */
    public Optional<FormativeExam> build(String title, Course c, List<String> specLines) {
        var fullSpec = specLines.stream()
                .collect(Collectors.joining("\n"));

        if (!this.svc.validate(fullSpec))
            return Optional.empty();

        var id = FormativeExamTitle.valueOf(title);

        return Optional.of(new FormativeExam(id, c, new FormativeExamSpecification(fullSpec)));
    }

}
