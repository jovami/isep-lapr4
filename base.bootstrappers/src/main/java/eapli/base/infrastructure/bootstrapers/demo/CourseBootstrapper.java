package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.course.domain.Course;
import eapli.framework.actions.Action;
import eapli.base.course.repositories.CourseRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CourseBootstrapper implements Action {

    @Override
    public boolean execute() {
        // some users that signup and are approved
        saveCourse("Fisica", "Fisica dos materiais", "20/05/2020","20/09/2020", 10,100);
        saveCourse("Quimica", "Quimicos/tabela periodica", "20/03/2020","20/01/2021", 1,200);
        saveCourse("Matematica", "Funcoes e tabuada", "20/03/2020","20/09/2020", 10,40);

        return true;
    }

    private void saveCourse(String name, String description, String startDate, String endDate, int min, int max) {
        CourseRepository repo = PersistenceContext.repositories().courses();
        Course c = new Course();
        c.setName(name);
        c.setDescription(description);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date sDate = null;
        Date eDate = null;
        try {
            sDate = df.parse(startDate);
            eDate = df.parse(endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Course course = new Course();
        c.setDuration(sDate,eDate);
        c.setCapacity(min,max);
        repo.save(course);
    }
}
