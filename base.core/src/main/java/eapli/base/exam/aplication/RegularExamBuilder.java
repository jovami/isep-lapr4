package eapli.base.exam.aplication;

import eapli.base.exam.domain.regular_exam.RegularExam;

import java.util.Date;

public class RegularExamBuilder implements RegularExamBuilderInterface{

    private RegularExam regularExam;
    public RegularExamBuilder()
    {
        this.regularExam = new RegularExam();
    }

    @Override
    public void setTitle(String title) {
        regularExam.setTitle(title);
    }

    @Override
    public void setHeader(String header) {
        regularExam.setHeader(header);
    }

    @Override
    public void setHeaderDescription(String headerDescription) {
        regularExam.setHeaderDescription(headerDescription);
    }

    @Override
    public void setExamDate(Date openDate, Date closeDate) {
        regularExam.setExamDate(openDate,closeDate);
    }

    public RegularExam getRegularExam(){
        return this.regularExam;
    }
}
