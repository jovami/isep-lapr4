package eapli.base.board.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Objects;


@Embeddable
public class BoardTitle implements Comparable<BoardTitle>, ValueObject {

    private String boardTitle;

    protected BoardTitle() {
    }

    protected BoardTitle(String boardTitle) {
        Preconditions.nonNull(boardTitle);
        Preconditions.nonEmpty(boardTitle);

        this.boardTitle = boardTitle;
    }

    public String title() {
        return boardTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BoardTitle that = (BoardTitle) o;
        return Objects.equals(boardTitle, that.boardTitle);
    }

    public static BoardTitle valueOf(String title){
        return new BoardTitle(title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardTitle);
    }

    @Override
    public int compareTo(BoardTitle o) {
        return boardTitle.compareTo(o.title());
    }
}
