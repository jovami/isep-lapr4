package eapli.base.examresult.dto.grade;

import java.util.ArrayList;

import eapli.base.exam.dto.CorrectedExamDTO;
import eapli.base.examresult.domain.ExamFeedbackProperties;
import eapli.base.examresult.domain.ExamGradeProperties;
import eapli.base.examresult.dto.grade.ExamResultDTO.Answer;
import eapli.base.examresult.dto.grade.ExamResultDTO.Section;

/**
 * ExamResultDTOMapper
 */
public class ExamResultDTOMapper {

    public ExamResultDTO toDTO(CorrectedExamDTO correction) {
        Float grade, maxGrade;
        grade = maxGrade = null;

        if (correction.gradeProps() != ExamGradeProperties.NONE) {
            grade = correction.grade();
            maxGrade = correction.maxGrade();
        }

        var sections = new ArrayList<Section>(correction.sections().size());
        for (final var section : correction.sections()) {
            var answers = new ArrayList<Answer>(section.getAnswers().size());

            for (final var answer : section.getAnswers()) {
                Float answerGrade, answerMaxGrade;
                answerGrade = answerMaxGrade = null;
                String feedback = null;

                if (correction.feedbackProps() != ExamFeedbackProperties.NONE)
                    feedback = answer.getFeedback();
                if (correction.gradeProps() != ExamGradeProperties.NONE) {
                    answerGrade = answer.getPoints();
                    answerMaxGrade = answer.getMaxPoints();
                }

                answers.add(new Answer(answerGrade, answerMaxGrade, feedback));
            }
            sections.add(new Section(answers));
        }

        return new ExamResultDTO(sections, grade, maxGrade);
    }
}
