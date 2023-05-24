package eapli.base.enrollment.application;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import eapli.base.clientusermanagement.domain.users.MecanographicNumber;
import eapli.base.course.domain.CourseID;

public class EnrollmentParser {

    private enum StudentColumns {

        MECANOGRAPHICNUMBER(0),

        // COURSE_ID(1);

        COURSE_ID(1);

        private final int col;

        StudentColumns(int col) {
            this.col = col;
        }
    }

    private Pair<MecanographicNumber, CourseID> parseLine(String[] line) {
        MecanographicNumber mecanographicNumber;
        CourseID courseName;

        mecanographicNumber = new MecanographicNumber(line[StudentColumns.MECANOGRAPHICNUMBER.col]);
        courseName = CourseID.valueOf(line[StudentColumns.COURSE_ID.col]);

        return Pair.of(mecanographicNumber, courseName);
    }

    public List<Pair<MecanographicNumber, CourseID>> parse(List<String[]> data) {

        return data.stream().map(this::parseLine).collect(Collectors.toList());
    }
}
