package eapli.base.course.application;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseState;
import eapli.base.course.repositories.CourseRepository;

/**
 * ListCoursesService
 */
public final class ListCoursesService {
    private final CourseRepository repo;

    public ListCoursesService(CourseRepository repo) {
        this.repo = repo;
    }

    public Iterable<Course> openable() {
        return this.repo.ofState(CourseState.CLOSE);
    }

    public Iterable<Course> enrollable() {
        return this.repo.ofState(CourseState.ENROLL);
    }

    public Iterable<Course> closable() {
        return withStates(CourseState.CLOSE, CourseState.OPEN, CourseState.ENROLL, CourseState.INPROGRESS);
    }

    public Iterable<Course> withStates(Set<CourseState> states) {
        return this.repo.ofStates(states);
    }

    public Iterable<Course> withStates(CourseState... states) {
        return withStates(Arrays.stream(states).collect(Collectors.toUnmodifiableSet()));
    }
}
