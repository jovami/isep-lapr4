package eapli.base.examresult.dto;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.exam.domain.regular_exam.RegularExamDate;
import eapli.base.examresult.domain.ExamGrade;

import java.util.Objects;

public class ExamGradeAndStudentDTO {
    private final ExamGrade grade;
    private final RegularExamDate date;
    private final Student student;

    public ExamGradeAndStudentDTO(ExamGrade grade, RegularExamDate date, Student student) {
        this.grade = grade;
        this.date = date;
        this.student = student;
    }

    public ExamGrade grade() {
        return this.grade;
    }

    public RegularExamDate date() {
        return this.date;
    }

    public Student student() {
        return this.student;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || obj.getClass() != this.getClass())
            return false;
        var o = (ExamGradeAndStudentDTO) obj;
        return this.student.equals(o.student) && this.date.equals(o.date) && this.grade.equals(o.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.student, this.date, this.grade);
    }

    @Override
    public String toString() {
        return " " + this.student
                + " | Date of exam: (" + this.date
                + ") | Grade: " + this.grade;
    }
}
