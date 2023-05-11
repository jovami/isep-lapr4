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
 * FormativeExamSpecification
 */
@Entity
public class FormativeExamSpecification implements AggregateRoot<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "formativeExamSpecificationId")
    private Long id;

    private FormativeExamTitle title;
    private FormativeExamDescription description;
    private List<FormativeExamSpecificationSection> sections;

    protected FormativeExamSpecification() {
        this.title = null;
        this.description = null;
        this.sections = null;
    }

    public FormativeExamSpecification(FormativeExamTitle title, FormativeExamDescription description,
            List<FormativeExamSpecificationSection> sections) {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sameAs'");
    }

    protected List<FormativeExamSpecificationSection> sections() {
        return this.sections;
    }

    protected FormativeExamTitle title() {
        return this.title;
    }
    protected FormativeExamDescription description() {
        return this.description;
    }

    @Override
    public Long identity() {
        return this.id;
    }

}
