package eapli.base.board.domain;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BoardColumn {

    @Id
    private int columnId;
    private String columnTitle;

    protected BoardColumn(){
    }
    public BoardColumn(int columnId){
        this.columnId = columnId;

    }

    public int getColumnId() {
        return columnId;
    }

    public String getColumnTitle() {
        return columnTitle;
    }
}
