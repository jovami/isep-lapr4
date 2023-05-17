package eapli.base.enrollment.aplication;


import eapli.base.clientusermanagement.domain.users.MecanographicNumber;
import org.apache.commons.lang3.tuple.Pair;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentParser {


    private enum StudentColumns {

        MECANOGRAPHICNUMBER(0),

        COURSE_ID(1);

        private final int col;
        StudentColumns(int col) {
            this.col = col;
        }
    }

    /**
     * @param data the data
     */
    public List<Pair<MecanographicNumber,Integer>> parse(List<String[]> data) {

        return data.stream().map(line -> {
                    MecanographicNumber  mecanographicNumber;
                    int courseID;



                    mecanographicNumber = new MecanographicNumber(line[StudentColumns.MECANOGRAPHICNUMBER.col]);
                    courseID = Integer.parseInt(line[StudentColumns.COURSE_ID.col]);


                    return Pair.of(mecanographicNumber,courseID);
                }).collect(Collectors.toList());

    }
}
