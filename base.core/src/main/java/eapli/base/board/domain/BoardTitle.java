package eapli.base.board.domain;


import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class BoardTitle implements Comparable<BoardTitle>, ValueObject {

    private String boardTitle;


    protected BoardTitle(){
    }
    public BoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardTitle that = (BoardTitle) o;
        return Objects.equals(boardTitle, that.boardTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardTitle);
    }

    @Override
    public int compareTo(BoardTitle o) {
        return boardTitle.compareTo(o.getBoardTitle());
    }
}
