package eapli.base.examresult.application;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.examresult.domain.ExamGradeProperties;
import eapli.base.examresult.domain.RegularExamResult;
import eapli.base.examresult.repository.RegularExamResultRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import org.eclipse.collections.impl.factory.HashingStrategySets;

import java.time.LocalDateTime;

import static org.eclipse.collections.impl.block.factory.HashingStrategies.fromFunction;

/**
 * ListCoursesService
 */
public final class ListExamResultsService {
    private final RegularExamResultRepository examResultRepo;

    public ListExamResultsService() {
        final var repos = PersistenceContext.repositories();

        this.examResultRepo = repos.examResults();
    }

    public Iterable<RegularExamResult> regularExamResultsBasedOnGradingProperties(final Student s) {
        final var results = HashingStrategySets.mutable.withAll(
                fromFunction(RegularExamResult::identity),
                this.examResultRepo.examResultsByStudent(s));

        results.removeIf(examResult -> examResult.gradeProperties() == ExamGradeProperties.NONE);
        results.removeIf(examResult ->
                examResult.gradeProperties() == ExamGradeProperties.AFTER_CLOSING
                && examResult.regularExam().date().closeDate().isAfter(LocalDateTime.now())
        );

        return results;
    }

    public Iterable<RegularExamResult> regularExamResultsBasedOnGradingProperties(final Course c) {
        final var results = HashingStrategySets.mutable.withAll(
                fromFunction(RegularExamResult::identity),
                this.examResultRepo.regularExamResultsByCourse(c));

        results.removeIf(examResult -> examResult.gradeProperties() == ExamGradeProperties.NONE);
        results.removeIf(examResult ->
                examResult.gradeProperties() == ExamGradeProperties.AFTER_CLOSING
                        && examResult.regularExam().date().closeDate().isAfter(LocalDateTime.now())
        );

        return results;
    }
}
