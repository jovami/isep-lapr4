package eapli.board;

import jovami.util.exceptions.ReceivedERRCode;

import java.io.*;

public class SBProtocol implements MessageProtocol{
    private static final Integer SBPRROTOCOL_VERSION = 1;
    private Integer code;
    private Integer dataLength;
    private byte[] content;

    //TODO: CODE FOR EACH request/response?? or for each of the actions??
    //SBProtocol codes
    public static final int COMMTEST = 0;
    public static final int DISCONN = 1;
    public static final int ACK = 2;
    public static final int ERR = 3;
    public static final int AUTH = 4;

    //BOARD SBPMessageCodes
    public static final int VIEW_ALL_BOARDS = 6;
    public static final int LIST_BOARDS = 7;
    public static final int CHOOSE_BOARD = 8;
    public static final int GET_BOARD = 9;
    public static final int GET_BOARDS_OWNED = 10;

    public SBProtocol(DataInputStream in) throws IOException, ReceivedERRCode {

        // SBProtocol PARAMETERS
        boolean isCompatible = (SBPRROTOCOL_VERSION == parseByte(in.readByte()));

        if (isCompatible) {
            code = parseByte(in.readByte());
            dataLength = readLength(in.readByte(), in.readByte());
            content = new byte[dataLength];

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

    public SBProtocol() {
        code = -1;
        dataLength = -1;
        content = null;
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

    private Integer readLength(byte b1, byte b2) {
        int dataLength1 = parseByte(b1);
        int dataLength2 = parseByte(b2);
        return dataLength1 + (dataLength2 * 256);
    }


    public boolean send(DataOutputStream out) throws IOException {
        out.write(SBPRROTOCOL_VERSION.byteValue());

        //TODO:ACCEPT NO CODE??
        /*if (code == -1) {
            out.write((byte) COMMTEST);
            out.write((byte) 0);
            out.write((byte) 0);
            return true;
        } else */
        if ((code == COMMTEST) || (code == DISCONN) || (code == ACK)) {
            // REQUESTS WITH THIS CODES CAN'T CONTAIN DATA
            out.write(code.byteValue());
            out.write((byte) 0);
            out.write((byte) 0);
            return true;
        }

        out.write(code.byteValue());
        if (dataLength != -1) {
            byte b_1 = (byte) (dataLength % 256);
            byte b_2 = (byte) (dataLength / 256);
            out.write(b_1);
            out.write(b_2);
            if ((content != null)) {
                out.write(content, 0, dataLength);
            }
        } else {
            out.write((byte) 0);
            out.write((byte) 0);
        }

        return true;
    }


    public void setContentFromString(String cStr) {
        dataLength = cStr.length();
        content = cStr.getBytes();
    }
}