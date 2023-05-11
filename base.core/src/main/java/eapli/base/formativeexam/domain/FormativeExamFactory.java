package eapli.base.formativeexam.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import eapli.base.exam.domain.question.Question;
import eapli.base.exam.domain.question.QuestionType;

/**
 * FormativeExamFactory
 */
public final class FormativeExamFactory {

    private Map<QuestionType, LinkedList<Question>> groupByType(Iterable<Question> questions) {
        var map = new EnumMap<QuestionType, LinkedList<Question>>(QuestionType.class);

        for (final var question : questions) {
            var type = question.type();
            map.putIfAbsent(type, new LinkedList<>());
            map.get(type).push(question);
        }

        var random = new Random(ProcessHandle.current().pid() * System.currentTimeMillis());

        map.forEach((__, list) -> Collections.shuffle(list, random));
        return map;
    }

    public FormativeExam build(FormativeExamSpecification specification,
            Iterable<Question> questions) {
        var questionsByType = groupByType(questions);

        var specSections = specification.sections();
        var sections = new ArrayList<FormativeExamSection>(specSections.size());

        for (final var specSection : specSections) {
            var finalQuestions = new ArrayList<FormativeExamQuestion>();

            for (final var questionInfo : specSection.questionInfos()) {
                final var typeQuestions = questionsByType.get(questionInfo.getKey());

                if (typeQuestions == null || typeQuestions.isEmpty())
                    throw new RuntimeException(
                            "not enough questions of type" + questionInfo.getKey());

                final var q = System.currentTimeMillis() % 2 == 0
                    ? typeQuestions.poll()
                    : typeQuestions.pollLast();

                finalQuestions.add(new FormativeExamQuestion(q, questionInfo.getValue()));
            }

            sections.add(new FormativeExamSection(finalQuestions, specSection.description()));
        }

        return new FormativeExam(specification.title(), specification.description(), sections);
    }
}
