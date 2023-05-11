package eapli.base.exam.aplication;

import java.util.Date;

public interface RegularExamBuilderInterface {
    void setTitle(String title);
    void setHeader(String header);
    void setHeaderDescription(String headerDescription);
    void setExamDate(Date openDate, Date closeDate);
}
