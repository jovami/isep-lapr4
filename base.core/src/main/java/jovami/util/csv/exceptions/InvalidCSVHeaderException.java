package jovami.util.csv.exceptions;

public class InvalidCSVHeaderException extends IllegalArgumentException {

    public InvalidCSVHeaderException() {
        super("The header of the CSV File is invallid!");
    }

    public InvalidCSVHeaderException(String s) {
        super(s);
    }
}
