package eapli.base.course.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.base.course.repositories.CourseRepository;

import java.util.Date;
public class CreateCourseController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CourseRepository repo;
    private Course course;
    private int id;

    public CreateCourseController() {
        repo = PersistenceContext.repositories().courses();
        course = null;
    }

    public boolean createCourse(String name,String description,Date startDate,Date endDate){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.MANAGER);
        try{
            course = new Course(name,description,startDate,endDate);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    public boolean addCapacity(int minCacapity,int maxCapacity){
        return course.setCapacity(minCacapity, maxCapacity);
    }
    public boolean saveCourse(){
        if (course==null){
            return false;
        }
        course = this.repo.save(course);
        return true;
    }
    public long countAll(){
        return repo.size();
    }

    public String courseString() {
        return course.toString();
    }
}
