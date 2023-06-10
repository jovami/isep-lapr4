package eapli.base.board.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    /*
     * @OneToOne(mappedBy = "cell")
     * private PostIt postIt;

     @OneToMany
     private final List<PostIt> postIts = new ArrayList<>();

     */
    protected Cell() {
    }

    public Cell(BoardRow row, BoardColumn column) {
        this.row = row;
        this.column = column;
        this.postIt = null;
    }

    public BoardRow getRow() {
        return row;
    }

    public BoardColumn getColumn() {
        return column;
    }
/*
    public List<PostIt> getPostIts(){return this.postIts;}

    public boolean addPostIt(PostIt postIt)
    {
        return this.postIts.add(postIt);
    }

    public boolean undoLastChangeInPostIt(PostIt postIt)
    {
        if (!postIts.isEmpty()) {
            int lastIndex = postIts.lastIndexOf(postIt);
            if (lastIndex != -1) {
                postIts.remove(lastIndex);
                postIt.undoLastChangeInPostIt();
                return true;
            }
            return false;
        }
        return false;
    }*/

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

    //TODO: implement
    public boolean hasPostIt() {
        return this.postIt!=null;
    }

    public void addPostIt(PostIt postIt){
        this.postIt=postIt;
    }

    public PostIt getPostIt() {
        return this.postIt;
    }
}
