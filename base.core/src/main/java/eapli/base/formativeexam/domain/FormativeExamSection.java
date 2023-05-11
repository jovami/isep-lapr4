package eapli.base.formativeexam.domain;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * FormativeExamSection
 */
@Embeddable
public class FormativeExamSection implements ValueObject {
    private final List<FormativeExamQuestion> questions;
    private final FormativeExamSectionDescription description;

    protected FormativeExamSection() {
        this.questions = null;
        this.description = null;
    }

    public FormativeExamSection(List<FormativeExamQuestion> questions, FormativeExamSectionDescription description) {
        Preconditions.nonNull(questions, "questions cannot be null");
        Preconditions.nonEmpty(questions, "Formative Exams must have at least one question");
        this.questions = new ArrayList<>(questions);
        this.description = description;
    }
}
