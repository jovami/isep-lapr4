package jovami.util.csv;

import java.util.List;

public interface CSVParser {
    /**
     * Parse.
     *
     * @param data the data
     */
    void parse(List<String[]> data);
}
