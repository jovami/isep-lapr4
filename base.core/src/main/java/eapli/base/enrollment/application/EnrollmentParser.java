package eapli.base.enrollment.application;

import java.util.List;
import java.util.stream.Collectors;

import eapli.base.course.domain.CourseName;
import org.apache.commons.lang3.tuple.Pair;

import eapli.base.clientusermanagement.domain.users.MecanographicNumber;

public class EnrollmentParser {

    private enum StudentColumns {

        MECANOGRAPHICNUMBER(0),

        //COURSE_ID(1);

        COURSE_NAME(1);

        private final int col;

        StudentColumns(int col) {
            this.col = col;
        }
    }

    /**
     * @param data the data
     */
    /*public List<Pair<MecanographicNumber, Integer>> parse(List<String[]> data) {

        return data.stream().map(line -> {
            MecanographicNumber mecanographicNumber;
            int courseID;

            mecanographicNumber = new MecanographicNumber(line[StudentColumns.MECANOGRAPHICNUMBER.col]);
            courseID = Integer.parseInt(line[StudentColumns.COURSE_ID.col]);

            return Pair.of(mecanographicNumber, courseID);
        }).collect(Collectors.toList());

    }*/

    public List<Pair<MecanographicNumber, CourseName>> parse(List<String[]> data) {

        return data.stream().map(line -> {
            MecanographicNumber mecanographicNumber;
            CourseName courseName;

            mecanographicNumber = new MecanographicNumber(line[StudentColumns.MECANOGRAPHICNUMBER.col]);
            courseName = CourseName.valueOf(line[StudentColumns.COURSE_NAME.col]);

            return Pair.of(mecanographicNumber, courseName);
        }).collect(Collectors.toList());

    }
}
