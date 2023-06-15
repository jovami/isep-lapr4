package eapli.base.examresult.dto;

import eapli.base.clientusermanagement.domain.users.MecanographicNumber;
import eapli.base.exam.domain.RegularExamDate;
import eapli.base.exam.domain.RegularExamTitle;
import eapli.base.examresult.domain.ExamGrade;

import java.util.Objects;

public class ExamGradeAndStudentDTO {
    private final RegularExamTitle title;
    private final ExamGrade grade;
    private final RegularExamDate date;
    private final MecanographicNumber mecanographicNumber;

    public ExamGradeAndStudentDTO(RegularExamTitle title, ExamGrade grade, RegularExamDate date, MecanographicNumber mecanographicNumber) {
        this.title = title;
        this.grade = grade;
        this.date = date;
        this.mecanographicNumber = mecanographicNumber;
    }

    public RegularExamTitle title() {
        return this.title;
    }

    public ExamGrade grade() {
        return this.grade;
    }

    public RegularExamDate date() {
        return this.date;
    }

    public MecanographicNumber mecanographicNumber() {
        return this.mecanographicNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || obj.getClass() != this.getClass())
            return false;
        var o = (ExamGradeAndStudentDTO) obj;
        return this.title.equals(o.title) && this.mecanographicNumber.equals(o.mecanographicNumber)
                && this.date.equals(o.date) && this.grade.equals(o.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.mecanographicNumber, this.date, this.grade);
    }

    @Override
    public String toString() {
        return "Exam: " + this.title
                + " | Mecanographic Number: (" + this.mecanographicNumber
                + " | Date of exam: (" + this.date
                + ") | Grade: " + this.grade;
    }
}
