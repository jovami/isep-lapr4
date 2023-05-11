package eapli.base.exam.domain.regular_exam;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="REGULAREXAMSECTION")
public class RegularExamSection implements AggregateRoot<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDRegularExamSection")
    private int id;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany
    @Column(name = "REGULAREXAMQUESTION")
    private List<RegularExamQuestion> regularExamQuestions;


    protected RegularExamSection(String description, List<RegularExamQuestion> regularExamQuestions)
    {
        this.description = description;
        this.regularExamQuestions = regularExamQuestions;
    }

    //FOR ORM ONLY
    public RegularExamSection()
    {

    }

    public List<RegularExamQuestion> regularExamQuestions(){return this.regularExamQuestions;}

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }


    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof RegularExamSection)) {
            return false;
        }

        final RegularExamSection that = (RegularExamSection) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
    }

    @Override
    public int compareTo(Integer other) {return AggregateRoot.super.compareTo(other);}

    @Override
    public Integer identity() {return this.id;}

    @Override
    public boolean hasIdentity(Integer id) {return AggregateRoot.super.hasIdentity(id);}
}
