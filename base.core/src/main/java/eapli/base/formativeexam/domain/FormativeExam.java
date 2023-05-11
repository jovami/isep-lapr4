package eapli.base.formativeexam.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

/**
 * FormativeExam
 */
@Entity
public class FormativeExam implements AggregateRoot<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "formativeExamId")
    private Long id;

    private FormativeExamTitle title;
    private FormativeExamDescription description;
    private List<FormativeExamSection> sections;

    protected FormativeExam() {
        this.title = null;
        this.description = null;
        this.sections = null;
    }

    public FormativeExam(FormativeExamTitle title, FormativeExamDescription description,
            List<FormativeExamSection> sections) {
        Preconditions.nonNull(title, "Formative Exam title cannot be null");
        Preconditions.nonNull(description, "Formative Exam description cannot be null");
        Preconditions.nonNull(sections, "Formative Exam sections cannot be null");
        Preconditions.nonEmpty(sections, "Formative Exams must have at least one section");

        this.title = title;
        this.description = description;
        this.sections = new ArrayList<>(sections);
    }

    @Override
    public boolean sameAs(Object other) {
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'sameAs'");
    }

    @Override
    public Long identity() {
        return this.id;
    }

}
