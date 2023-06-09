package eapli.base.board.dto;

import eapli.base.board.domain.Board;

import java.util.Objects;

public class BoardDTO {

    private final Board board;

    public BoardDTO(Board board)
    {
        this.board = board;
    }

    public Board board(){return this.board;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || obj.getClass() != this.getClass())
            return false;
        var o = (Board) obj;
        return this.board.getBoardTitle().equals(o.getBoardTitle()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.board);
    }

    @Override
    public String toString() {
        return " "  + board;
    }
}
