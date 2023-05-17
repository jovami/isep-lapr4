package eapli.base.enrollment.aplication;


import eapli.base.infrastructure.persistence.PersistenceContext;
import jovami.util.csv.CSVHeader;
import jovami.util.csv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


public class CSVLoaderStudentsController {


    private BulkEnrollStudentsService service;
    public CSVLoaderStudentsController(){

        service = new BulkEnrollStudentsService(PersistenceContext.repositories());

    }

    public void file(File file) throws FileNotFoundException {
        List<String[]> list =  CSVReader.readCSV(file,CSVHeader.ENROLLMENTS);

        var parsedList = new EnrollmentParser().parse(list);

       this.service.bulkEnroll(parsedList);
    }



}
