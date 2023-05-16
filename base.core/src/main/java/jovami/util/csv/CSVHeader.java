package jovami.util.csv;

public enum CSVHeader {
    /**
     * The No header.
     */
    NO_HEADER(0, ",") {
        @Override
        public String toString() {
            return null;
        }
    },

    STUDENTS(9, ",") {
        @Override
        public String toString() {
            return "username,password,firstname,lastname,fullname,email,dateofbirth,taxpayernumber,mecanographicnumber";
        }
    };


    private final int columns;
    private final String delimiter;

    /**
     * Gets column count.
     *
     * @return the column count
     */
    public int getColumnCount() {
        return this.columns;
    }

    /**
     * Gets delimiter.
     *
     * @return the delimiter
     */
    public String getDelimiter() {
        return this.delimiter;
    }

    CSVHeader(int col, String delim) {
        this.columns = col;
        this.delimiter = delim;
    }
}
