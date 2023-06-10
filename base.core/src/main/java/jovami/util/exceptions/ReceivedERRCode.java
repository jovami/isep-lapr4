package jovami.util.exceptions;

public class ReceivedERRCode extends Exception {
    // Parameterless Constructor
    public ReceivedERRCode() {
    }

    // Constructor that accepts a message
    public ReceivedERRCode(String message) {
        super(message);
    }
}
