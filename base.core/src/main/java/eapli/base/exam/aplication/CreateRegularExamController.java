package eapli.base.exam.aplication;


import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.base.exam.repositories.RegularExamRepository;

import java.util.Date;


public class CreateRegularExamController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RegularExamRepository repo;
    private RegularExam regularExam;
    private int id;

    public CreateRegularExamController()
    {
        repo = PersistenceContext.repositories().exams();
        //regularExam = new RegularExam();
    }

    public boolean createRegularExam(String title, String header, String headerDescription, Date openDate, Date closeDate)
    {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.TEACHER);
        /*regularExam.setTitle(title);
        regularExam.setHeader(header);
        regularExam.setHeaderDescription(headerDescription);
        regularExam.setExamDate(openDate,closeDate);*/

        return true;
    }

    public boolean saveRegularExam(){
        if (regularExam==null){
            return false;
        }
        regularExam = this.repo.save(regularExam);
        return true;
    }

    public String regularExamString()
    {
        return regularExam.toString();
    }
}
