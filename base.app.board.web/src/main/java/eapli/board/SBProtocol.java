package eapli.board;

import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SBProtocol implements MessageProtocol {
    private static final Integer SBPRROTOCOL_VERSION = 1;
    private static final int CR = 13;
    private Integer code;
    private Integer dataLength;
    private String authToken;
    private byte[] content;

    // SBProtocol codes
    public static final int COMMTEST = 0;
    public static final int DISCONN = 1;
    public static final int ACK = 2;
    public static final int ERR = 3;
    public static final int AUTH = 4;

    // BOARD SBPMessageCodes
    public static final int VIEW_ALL_BOARDS = 6;
    public static final int LIST_BOARDS = 7;
    public static final int CHOOSE_BOARD = 8;
    public static final int GET_BOARD = 9;
    public static final int SHARE_BOARD = 10;
    public static final int GET_BOARDS_OWNED_NOT_ARCHIVED = 11;
    public static final int GET_BOARDS_OWNED_ARCHIVED = 12;
    public static final int CREATE_POST_IT = 13;
    public static final int UPDATE_POST_IT = 19;
    public static final int MOVE_POST_IT = 20;
    public static final int SEND_POST_IT_INFO = 14;
    public static final int GET_BOARDS_USER_PARTICIPATES_AND_HAS_WRITE_PERMISSIONS = 21;
    public static final int LIST_HISTORY = 15;
    public static final int VIEW_BOARD_HISTORY = 16;
    public static final int TOKEN = 17;
    public static final int UNDO_LAST_POST_IT_CHANGE = 18;
    public static final int VIEW_NOTFS = 21;

    public SBProtocol(DataInputStream in) throws IOException, ReceivedERRCode {

        // SBProtocol PARAMETERS
        boolean isCompatible = (SBPRROTOCOL_VERSION == parseByte(in.readByte()));
        if (isCompatible) {
            code = parseByte(in.readByte());
            dataLength = parseLength(in.readByte(), in.readByte());
            content = new byte[dataLength];
            readToken(in);
            in.readFully(content, 0, dataLength);
            if (code == SBProtocol.ERR && dataLength != 0) {
                throw new ReceivedERRCode(getContentAsString());
            } else if (code == SBProtocol.ERR) {
                throw new ReceivedERRCode();
            }
        } else {
            throw new IllegalArgumentException("Incompatible Message Version");
        }
    }

    private String readToken(DataInputStream in) throws IOException {
        String ret = "";
        int val;
        do {
            val = in.read();
            if (val == -1)
                throw new IOException();
            if (val != CR)
                ret = ret + (char) val;
        } while (val != CR);

        if (ret.equals("")) {
            authToken = null;
        } else {
            authToken = ret;
        }
        return ret;
    }

    public SBProtocol() {
        code = -1;
        dataLength = -1;
        content = null;
        authToken = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    private int parseByte(Byte b) {
        return b & 0xFF;
    }

    public boolean hasContent() {
        return (content != null);
    }

    public String getContentAsString() {
        if (dataLength == 0) {
            return "";
        }
        return (new String(content));
    }

    public byte[] getContent() {
        return content;
    }

    private Integer parseLength(byte b1, byte b2) {
        int dataLength1 = parseByte(b1);
        int dataLength2 = parseByte(b2);
        return dataLength1 + (dataLength2 * 256);
    }

    public void setToken(String token) {
        this.authToken = token;
    }

    public String token() {
        return this.authToken;
    }

    public boolean send(DataOutputStream out) throws IOException {
        out.write(SBPRROTOCOL_VERSION.byteValue());

        /*
         * if (code == -1) {
         * out.write((byte) COMMTEST);
         * out.write((byte) 0);
         * out.write((byte) 0);
         * return true;
         * } else
         */
        if ((code == COMMTEST) || (code == DISCONN) || (code == ACK)) {
            // REQUESTS WITH THIS CODES CAN'T CONTAIN DATA
            out.write(code.byteValue());
            out.write((byte) 0);
            out.write((byte) 0);
            writeToken(out);
            return true;
        }

        out.write(code.byteValue());
        if (dataLength != -1) {
            byte b_1 = (byte) (dataLength % 256);
            byte b_2 = (byte) (dataLength / 256);
            out.write(b_1);
            out.write(b_2);
        } else {
            out.write((byte) 0);
            out.write((byte) 0);
        }
        writeToken(out);
        if ((content != null)) {
            out.write(content, 0, dataLength);
        }
        return true;
    }

    private void writeToken(DataOutputStream out) throws IOException {
        if (authToken!=null)
            out.write(authToken.getBytes());
        out.write(CR);
    }

    public void setContentFromString(String cStr) {
        dataLength = cStr.length();
        content = cStr.getBytes();
    }

    public static void sendErr(String messageErr, DataOutputStream outS) throws IOException {
        SBProtocol p = new SBProtocol();
        p.setCode(ERR);
        if (messageErr != null)
            p.setContentFromString(messageErr);
        p.send(outS);
    }
}
