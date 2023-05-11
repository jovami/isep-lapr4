package eapli.base.board.domain;


import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

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
    public int compareTo(BoardTitle o) {
        return boardTitle.compareTo(o.getBoardTitle());
    }
}
