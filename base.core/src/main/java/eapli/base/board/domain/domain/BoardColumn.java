package eapli.base.board.domain.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardColumn that = (BoardColumn) o;
        return columnId == that.columnId && Objects.equals(columnTitle, that.columnTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnId, columnTitle);
    }


}
