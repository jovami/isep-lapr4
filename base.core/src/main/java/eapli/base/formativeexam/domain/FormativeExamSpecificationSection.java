package eapli.base.formativeexam.domain;

import java.util.List;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.tuple.Pair;

import eapli.base.exam.domain.question.QuestionType;
import eapli.framework.domain.model.ValueObject;

/**
 * FormativeExamSpecificationSection
 */
@Embeddable
public class FormativeExamSpecificationSection implements ValueObject {
    private final FormativeExamSectionDescription description;
    private final List<Pair<QuestionType, QuestionWeight>> questionsInfos;

    protected FormativeExamSpecificationSection() {
        this.description = null;
        this.questionsInfos = null;
    }

    public FormativeExamSpecificationSection(FormativeExamSectionDescription description, List<Pair<QuestionType, QuestionWeight>> questionInfos) {
        this.description = description;
        this.questionsInfos = questionInfos;
    }

    protected FormativeExamSectionDescription description() {
        return this.description;
    }
    protected List<Pair<QuestionType, QuestionWeight>> questionInfos() {
        return this.questionsInfos;
    }
}
