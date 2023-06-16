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
        this.time = split[3];
        System.out.println(str);
        System.out.println(Arrays.toString(split));
        System.out.println(split.length);

        System.out.println(type);
        if (type.equals("CREATE")) {
            this.prevText = null;
            this.posText = split[4];
        } else {
            this.prevText = split[4];
            this.posText = split[5];
        }

    }


    @Override
    public String toString() {
        return String.format("[%-6s] | %-10s | [%-2s,%-2s] | %-16s | %-20s | %s",
                type, board, position[0], position[1], time, prevText, posText);
    }


}
