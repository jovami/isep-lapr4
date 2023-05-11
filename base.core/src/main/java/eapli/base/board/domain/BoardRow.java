package eapli.base.board.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BoardRow {

    @Id
    private int rowId;
    private String rowTitle;

    protected BoardRow(){
    }
    public BoardRow(int rowId){
        this.rowId = rowId;
    }

    public int getRowId() {
        return rowId;
    }

    public String getRowTitle() {
        return rowTitle;
    }
}
