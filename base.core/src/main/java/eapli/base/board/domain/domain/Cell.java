package eapli.base.board.domain.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Cell {
    @Id
    private int cellId;

    @OneToOne
    private BoardRow row;

    @OneToOne
    private BoardColumn column;


    /*@OneToOne(mappedBy = "cell")
    private PostIt postIt;*/


//@OneToMany
    //private final List<PostIt> postIts = new ArrayList<>();


    protected Cell() {
    }
    public Cell(int cellId, BoardRow row, BoardColumn column) {
        this.cellId = cellId;
        this.row = row;
        this.column = column;
    }

    public int getCellId() {
        return cellId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return cellId == cell.cellId && Objects.equals(row, cell.row) && Objects.equals(column, cell.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellId, row, column);
    }
}

