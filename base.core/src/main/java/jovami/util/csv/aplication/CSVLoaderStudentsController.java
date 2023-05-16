package jovami.util.csv.aplication;


import jovami.util.csv.CSVHeader;
import jovami.util.csv.CSVParser;
import jovami.util.csv.CSVReader;
import jovami.util.csv.StudentParser;

import java.io.IOException;
import java.util.*;


public class CSVLoaderStudentsController {

    private enum CSVFiles {
        STUDENTS("students%s.csv", CSVHeader.STUDENTS);

        private final String fname;
        private final CSVHeader header;

        private static final String PREFIX = "/csvfiles";

        /**
         * Path string.
         *
         * @return the string
         */
        public String path() {
            return String.format("%s/%s",
                    PREFIX, this.fname);
        }

        CSVFiles(String s, CSVHeader h) {
            this.fname = s;
            this.header = h;
        }
    }

    private final Map<CSVHeader, CSVParser> parsers;

    public CSVLoaderStudentsController(){
        this.parsers = new EnumMap<>(CSVHeader.class);
        this.parsers.put(CSVHeader.STUDENTS, new StudentParser());
    }

    public void loadResources()
    {
        for (var fileEnum : CSVFiles.values()) {
            String fpath = fileEnum.path();
            List<String[]> data;

            try {
                data = CSVReader.readFromResources(fpath, fileEnum.header);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            this.parsers.get(fileEnum.header).parse(data);
        }
    }

}
