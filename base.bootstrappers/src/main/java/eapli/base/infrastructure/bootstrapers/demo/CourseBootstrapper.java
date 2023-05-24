package eapli.base.infrastructure.bootstrapers.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseFactory;
import eapli.base.course.domain.CourseState;
import eapli.base.course.dto.CreateCourseDTO;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;

public class CourseBootstrapper implements Action {

    @Override
    public boolean execute() {
        // some users that signup and are approved
        saveCourse("Fisica", 1L, "Fisica dos materiais", "20/05/2020", "20/09/2020", 10, 100, CourseState.OPEN);
        saveCourse("Quimica", 1L, "Quimicos/tabela periodica", "20/03/2020", "20/01/2021", 1, 200, CourseState.OPEN);
        saveCourse("Matematica", 2L, "Funcoes e tabuada", "20/03/2020", "20/09/2020", 10, 40, CourseState.OPEN);
        saveCourse("Tugues", 3L, "Miseria e Desgraca", "20/03/2020", "20/09/2020", 11, 50, CourseState.ENROLL);
        saveCourse("Estatistica", 1L, "Poisson stuff", "20/09/2020", "20/09/2021", 5, 40, CourseState.ENROLL);

        return true;
    }

    private void saveCourse(String name, Long code, String description, String startDate, String endDate, int min,
            int max,
            CourseState state) {
        CourseRepository repo = PersistenceContext.repositories().courses();
        var df = DateTimeFormatter.ofPattern("d/M/yyyy");

        var sDate = LocalDate.parse(startDate, df);
        var eDate = LocalDate.parse(endDate, df);

        var dto = new CreateCourseDTO(name, code, description, sDate, eDate, min, max);

        try {
            Course c = new CourseFactory().build(dto);
            setCourseState(c, state);
            repo.save(c);
        } catch (IllegalArgumentException e) {
            System.out.printf("Course %s was not bootstrapped\n", name);
            throw new RuntimeException(e);
        }
    }

    private void setCourseState(Course course, CourseState state) {
        switch (state) {
            case ENROLL:
                course.open();
                course.openEnrollments();
                break;
            case CLOSED:
                course.close();
                break;
            case INPROGRESS:
                course.open();
                course.openEnrollments();
                course.closeEnrollments();
                break;
            case OPEN:
                course.open();
                break;
            default:
                break;
        }

    }
}
