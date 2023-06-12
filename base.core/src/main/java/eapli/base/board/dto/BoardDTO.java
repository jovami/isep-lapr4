package eapli.base.board.dto;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "title")
public class BoardDTO {

    private BoardTitle title;
    private int column;
    private int row;

    public BoardDTO(Board board)
    {
        this.title=board.getBoardTitle();
        this.row = board.getNumRows();
        this.column = board.getNumColumns();
    }
    public BoardTitle getTitle() {
        return title;
    }
    public int getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }

    @Override
    public String toString(){
        return "Board Title: " + title+
                "\nColumns: "+column+
                "\nRows: "+row+"\n";
    }
}
