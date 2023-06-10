package eapli.base.board.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;

@Entity
@Table(name = "BOARD")
public class Board implements AggregateRoot<BoardTitle> {


    @Id
    private BoardTitle boardTitle;
    @Transient
    public static int MAX_ROWS;
    @Transient
    public static int MAX_COLUMNS;
    @Column(nullable = false)
    private int num_rows;
    @Column(nullable = false)
    private int num_columns;
    @OneToOne
    private SystemUser owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardState state;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<Cell> cells = new ArrayList<>();

    @ElementCollection
    private final List<BoardColumn> boardColumnList = new ArrayList<>();

    @ElementCollection
    private final List<BoardRow> boardRowList = new ArrayList<>();

    protected Board() {
    }


    public Board(BoardTitle boardTitle, int rows, int columns, SystemUser owner) {
        Preconditions.noneNull(boardTitle, rows, columns, owner);

        this.boardTitle = boardTitle;
        setDimension(rows, columns);
        this.owner = owner;
        this.state = BoardState.CREATED;
        setupBoard(rows, columns);
    }

    public void setDimension(int num_rows,int num_columns) {
        if (num_rows > MAX_ROWS || num_columns > MAX_COLUMNS || num_rows < 1 || num_columns < 1) {
            throw new IllegalArgumentException("Board dimension is not valid");
        }
        this.num_rows = num_rows;
        this.num_columns = num_columns;
    }



    public static void setMax(int maxRows, int maxColumns) {
        MAX_ROWS = maxRows;
        MAX_COLUMNS = maxColumns;
    }


    public void archiveBoard() {
        this.state = BoardState.ARCHIVED;
    }

    public void sharedBoard() {
        this.state = BoardState.SHARED;
    }

    public void createdBoard() {
        this.state = BoardState.CREATED;
    }

    public BoardState getState() {
        return state;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<BoardColumn> getBoardColumnList() {
        return boardColumnList;
    }

    public List<BoardRow> getBoardRowList() {
        return boardRowList;
    }

    public BoardTitle getBoardTitle() {
        return boardTitle;
    }

    public SystemUser boardOwner(){return owner;}

    public void setupBoard(int rows, int columns) {
        addRowIds(rows);
        addColumnIds(columns);
        createCells(rows, columns);
    }

    public void createCells(int rows, int columns) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells.add(new Cell(boardRowList.get(row), boardColumnList.get(column)));
            }
        }
    }

    public void addRowIds(int lastColumn) {
        for (int i = 0; i < lastColumn; i++) {
            boardRowList.add(new BoardRow(i));
        }
    }

    public void addColumnIds(int lastRow) {
        for (int i = 0; i < lastRow; i++) {
            boardColumnList.add(new BoardColumn(i));
        }
    }

    public PostIt createPostIt(int cellId)
    {
        PostIt postIt = new PostIt(cellId);
        cells.get(cellId).addPostIt(postIt);
        return postIt;
    }

    public boolean registerChangeInPostIt(int cellId, PostIt postIt)
    {
        return cells.get(cellId).addPostIt(postIt);
    }


    /*
     * public void movePostIt(int newCellId, PostIt postIt) {
     * //if newCellId has not a post it assigned
     *
     * if (!hasCellPostIt(newCellId)) {
     * postIt.alterCell(newCellId);
     * } else
     * System.out.println("Cell Already Occupied");
     *
     *
     * }
     */

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Board)) {
            return false;
        }

        final Board o = (Board) other;
        if (this == o) {
            return true;
        }
        return this.boardTitle.equals(o.boardTitle);
    }

    @Override
    public BoardTitle identity() {
        return boardTitle;
    }

    @Override
    public String toString() {
        return " Board: " +
                "\nTitle: " + boardTitle.title() +
                ", with " + cells.size() + " Cells";
    }
}
