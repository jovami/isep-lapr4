package eapli.base.board.domain;

import javax.persistence.*;

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








}

