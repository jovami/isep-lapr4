package eapli.base.infrastructure.bootstrapers.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseDescription;
import eapli.base.course.domain.CourseName;
import eapli.base.course.domain.CourseState;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;

public class CourseBootstrapper implements Action {

    @Override
    public boolean execute() {
        // some users that signup and are approved
        saveCourse("Fisica", "Fisica dos materiais", "20/05/2020", "20/09/2020", 10, 100, CourseState.OPEN);
        saveCourse("Quimica", "Quimicos/tabela periodica", "20/03/2020", "20/01/2021", 1, 200, CourseState.OPEN);
        saveCourse("Matematica", "Funcoes e tabuada", "20/03/2020", "20/09/2020", 10, 40, CourseState.OPEN);
        saveCourse("Tugues", "Miseria e Desgraca", "20/03/2020", "20/09/2020", 11, 50, CourseState.ENROLL);
        saveCourse("Estatistica", "Poisson stuff", "20/09/2020", "20/09/2021", 5, 40, CourseState.ENROLL);

        return true;
    }

    private void saveCourse(String name, String description, String startDate, String endDate, int min, int max,
            CourseState state) {
        CourseRepository repo = PersistenceContext.repositories().courses();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date sDate = null;
        Date eDate = null;

        try {
            sDate = df.parse(startDate);
            eDate = df.parse(endDate);
        } catch (ParseException e) {
            System.out.printf("Course %s was not bootstrapped\n because Duration was not right", name);
        }

        try {
            Course c = new Course(CourseName.valueOf(name), CourseDescription.valueOf(description), sDate, eDate);
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
                course.openEnrollments();
                break;
            case CLOSED:
                course.close();
                break;
            case INPROGRESS:
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
