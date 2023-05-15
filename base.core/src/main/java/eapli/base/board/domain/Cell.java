package eapli.base.board.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Cell implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cellId;

    private BoardRow row;
    private BoardColumn column;


    /*@OneToOne(mappedBy = "cell")
    private PostIt postIt;*/


//@OneToMany
    //private final List<PostIt> postIts = new ArrayList<>();


    protected Cell() {
    }
    public Cell(BoardRow row, BoardColumn column) {
        this.row = row;
        this.column = column;
    }

    public BoardRow getRow() {
        return row;
    }

    public BoardColumn getColumn() {
        return column;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return  Objects.equals(row, cell.row) && Objects.equals(column, cell.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}

