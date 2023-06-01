package eapli.base.examresult.dto;

import eapli.base.clientusermanagement.domain.users.MecanographicNumber;
import eapli.base.exam.domain.regular_exam.RegularExamDate;
import eapli.base.examresult.domain.ExamGrade;

import java.util.Objects;

public class ExamGradeAndStudentDTO {
    private final ExamGrade grade;
    private final RegularExamDate date;
    private final MecanographicNumber mecanographicNumber;

    public ExamGradeAndStudentDTO(ExamGrade grade, RegularExamDate date, MecanographicNumber mecanographicNumber) {
        this.grade = grade;
        this.date = date;
        this.mecanographicNumber = mecanographicNumber;
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
        return this.mecanographicNumber.equals(o.mecanographicNumber) && this.date.equals(o.date) && this.grade.equals(o.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mecanographicNumber, this.date, this.grade);
    }

    @Override
    public String toString() {
        return " " + this.mecanographicNumber
                + " | Date of exam: (" + this.date
                + ") | Grade: " + this.grade;
    }
}
