package eapli.base.exam.domain.regular_exam;

import eapli.base.course.domain.Course;
import eapli.base.exam.domain.RegularExamDate;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
@Deprecated
public class RegularExamId implements ValueObject {
    @ManyToOne
    private Course course;
    private RegularExamDate date;

    protected RegularExamId() {
        this.date = null;
        this.course = null;
    }

    protected RegularExamId(RegularExamDate date, Course course) {
        Preconditions.noneNull(date, course);

        this.date = date;
        this.course = course;
    }

    public static RegularExamId valueOf(RegularExamDate date, Course course) {
        return new RegularExamId(date, course);
    }
}
