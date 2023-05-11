package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.framework.actions.Action;
import eapli.base.exam.repositories.RegularExamRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegularExamBootstrapper implements Action{

    @Override
    public boolean execute()
    {
        saveExam("Eapli","Engenharia de aplicacoes","exame de eapli","10/07/2022","10/07/2020");
        saveExam("RCOMP","Redes de computadores","exame de redes","11/07/2022","11/07/2020");
        saveExam("SCOMP","Sistemas de computadores","exame de scomp","12/07/2022","12/07/2020");
        saveExam("LAPR4","Laboratorio de projecto","exame de lapr","13/07/2022","13/07/2020");
        saveExam("LPROG","Linguagens de programacao","exame de lprog","14/07/2022","14/07/2020");


        return true;
    }

    private void saveExam(String title, String header, String headerDescription, String openDate, String closeDate)
    {
        RegularExamRepository repo = PersistenceContext.repositories().exams();
        RegularExam e = new RegularExam();

        e.setTitle(title);
        e.setHeader(header);
        e.setHeaderDescription(headerDescription);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date oDate = null;
        Date cDate = null;
        try {
            oDate = df.parse(openDate);
            cDate = df.parse(closeDate);
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }

        RegularExam regularExam = new RegularExam();
        e.setExamDate(oDate,cDate);
        repo.save(regularExam);

    }
}
