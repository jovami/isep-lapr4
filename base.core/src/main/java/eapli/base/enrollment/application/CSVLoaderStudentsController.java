package eapli.base.enrollment.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import eapli.base.enrollment.domain.Enrollment;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import jovami.util.csv.CSVHeader;
import jovami.util.csv.CSVReader;

@UseCaseController
public class CSVLoaderStudentsController {

    private BulkEnrollStudentsService service;

    public CSVLoaderStudentsController() {

        service = new BulkEnrollStudentsService(PersistenceContext.repositories());

    }

    public void file(File file) throws FileNotFoundException {
        List<String[]> list = CSVReader.readCSV(file, CSVHeader.ENROLLMENTS);

        var parsedList = new EnrollmentParser().parse(list);

        this.service.bulkEnroll(parsedList);
    }

    public Iterable<Enrollment> listAllEnrollmentsInAllCourses() {
        return this.service.listAllEnrollmentsInAllCourses();
    }
}
