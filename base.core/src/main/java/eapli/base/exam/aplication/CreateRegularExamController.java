package eapli.base.exam.aplication;


import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.base.exam.repositories.RegularExamRepository;

import java.util.Date;


public class CreateRegularExamController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RepositoryFactory repositoryFactory;
    private final RegularExamRepository repoRegularExam;

    private RegularExam regularExam;

    public CreateRegularExamController()
    {
        this.repositoryFactory = PersistenceContext.repositories();
        this.repoRegularExam = repositoryFactory.regularExams();
        regularExam = null;
    }

    public boolean createRegularExam(String title, String header, String headerDescription, Date openDate, Date closeDate)
    {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.TEACHER);

        String regularExamSpecification = title + header + headerDescription ;


        try{
            regularExam = new RegularExam(regularExamSpecification, openDate,closeDate);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }


    public boolean saveRegularExam(){
        if (regularExam==null){
            return false;
        }
        regularExam = this.repoRegularExam.save(regularExam);
        return true;
    }

    public Iterable<RegularExam> lstRegularExams(){
        return this.repoRegularExam.findAll();
    }

    public String regularExamString()
    {
        return regularExam.toString();
    }
}
