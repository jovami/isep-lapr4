package eapli.base.board.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
public class Cell implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cellId;

    @Embedded
    private BoardRow row;

    @Embedded
    private BoardColumn column;
    @OneToOne
    private PostIt postIt;


    protected Cell() {
    }

    public Cell(BoardRow row, BoardColumn column) {
        this.row = row;
        this.column = column;
        this.postIt = null;
    }

    public void addPostIt(PostIt postIt) {
        synchronized (this) {
            if (hasPostIt()) {
                throw new IllegalStateException("Cell already has a PostIt");
            }
            this.postIt = postIt;

        }
    }

    public void removePostIt() {
        synchronized (this) {
            this.postIt = null;
        }
    }

    public void deletePostIt() {
        synchronized (this) {
            this.postIt = null;
        }
    }

    public String getPostItData() {
        return this.postIt.getData();
    }

    public BoardRow getRow() {
        return row;
    }

    public BoardColumn getColumn() {
        return column;
    }

    public PostIt getPostIt() {
        return this.postIt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cell cell = (Cell) o;
        return Objects.equals(row, cell.row) && Objects.equals(column, cell.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public boolean hasPostIt() {
        return this.postIt != null;
    }


}
