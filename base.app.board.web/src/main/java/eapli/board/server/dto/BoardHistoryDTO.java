package eapli.board.server.dto;

import com.ibm.icu.text.SimpleDateFormat;
import eapli.base.board.domain.BoardParticipant;
import lombok.Getter;

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
    private final String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardHistoryDTO that = (BoardHistoryDTO) o;
        return Objects.equals(type, that.type) && Objects.equals(board, that.board) && Objects.equals(position, that.position) && Objects.equals(time, that.time) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, board, position[0],position[1], time, text);
    }

    public BoardHistoryDTO(String str) {
        String[] split = str.split("\t");
        this.type = split[0];
        this.board = split[1];
        this.position = split[2].split(",");
        this.time = split[3];
        this.text = split[4];
    }




    @Override
    public String toString() {
        return String.format("[%-6s] | %-10s | [%-2s,%-2s] | %-16s | %s",
                type, board, position[0],position[1], time, text);
    }



}
