package eapli.base.board.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class BoardRow {

    @Id
    private int rowId;
    private String rowTitle;

    protected BoardRow() {
    }

    public BoardRow(int rowId) {
        this.rowId = rowId;
    }

    public int getRowId() {
        return rowId;
    }

    public String getRowTitle() {
        return rowTitle;
    }
    public void setRowTitle(String rowTitle){
        this.rowTitle = rowTitle;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardRow boardRow = (BoardRow) o;
        return rowId == boardRow.rowId && Objects.equals(rowTitle, boardRow.rowTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowId, rowTitle);
    }

}
