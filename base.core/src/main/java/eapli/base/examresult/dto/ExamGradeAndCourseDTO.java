package eapli.base.examresult.dto;

import eapli.base.course.domain.CourseID;
import eapli.base.exam.domain.RegularExamDate;
import eapli.base.examresult.domain.ExamGrade;

import java.util.Objects;

public class ExamGradeAndCourseDTO {
    private final ExamGrade grade;
    private final RegularExamDate date;
    private final CourseID id;

    public ExamGradeAndCourseDTO(ExamGrade grade, RegularExamDate date, CourseID id) {
        this.grade = grade;
        this.date = date;
        this.id = id;
    }

    public ExamGrade grade() {
        return this.grade;
    }

    public RegularExamDate date() {
        return this.date;
    }

    public CourseID courseId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || obj.getClass() != this.getClass())
            return false;
        var o = (ExamGradeAndCourseDTO) obj;
        return this.id.equals(o.id) && this.date.equals(o.date) && this.grade.equals(o.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.date, this.grade);
    }

    @Override
    public String toString() {
        return "Course: " + this.id
                + " | Date of exam: (" + this.date
                + ") | Grade: " + this.grade;
    }
}
