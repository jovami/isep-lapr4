package eapli.base.board.dto;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

public class BoardHistoryDTO {

    @Getter
    private final String type;
    @Getter
    private final String board;
    @Getter
    private final String[] position;
    @Getter
    private final String time;
    @Getter
    private final String prevText;
    @Getter
    private final String posText;
    private final int pos0;
    private final int pos1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardHistoryDTO that = (BoardHistoryDTO) o;
        return Objects.equals(getType(), that.getType()) && Objects.equals(getBoard(), that.getBoard())
                && Arrays.equals(getPosition(), that.getPosition()) && Objects.equals(getTime(), that.getTime())
                && Objects.equals(getPrevText(), that.getPrevText()) && Objects.equals(getPosText(), that.getPosText());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getType(), getBoard(), getTime(), getPrevText(), getPosText());
        result = 31 * result + Arrays.hashCode(getPosition());
        return result;
    }

    public BoardHistoryDTO(String str) {
        String[] split = str.split("\t");
        this.type = split[0];
        this.board = split[1];
        this.position = split[2].split(",");
        pos0 = Integer.parseInt(position[0]) + 1;
        pos1 = Integer.parseInt(position[1]) + 1;
        this.time = split[3];

        switch (type){
            case "CREATE":
                this.prevText = null;
                this.posText = split[4];
                break;
            case "REMOVE":
                this.prevText = split[4];
                this.posText = null;
                break;
            default:
                this.prevText = split[4];
                this.posText = split[5];
                break;
        }
    }


    @Override
    public String toString() {
        return String.format("[%-6s] | %-10s | [%-2s,%-2s] | %-19s | %-20s | %s",
                type, board, pos0, pos1, time, parseIfImage(prevText), parseIfImage(posText));
    }

    private String parseIfImage(String content) {
        String prev;
        if (content==null){
            return null;
        }
        if (content.startsWith("\"")&& content.endsWith("\"")){
            String[] tmp = content.split("/");
            prev = "\""+ tmp[tmp.length-1];
        }else {
            prev = content;
        }
        return prev;
    }


}
