package eapli.base.board.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

@Entity
@Table(name = "BOARD")
public class Board implements AggregateRoot<BoardTitle> {

    @Id
    private BoardTitle boardTitle;
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

    // TODO protected??

    protected Board(String boardTitle, int rows, int columns) {
        this.boardTitle = new BoardTitle(boardTitle);
        this.num_rows = rows;
        this.num_columns = columns;
        this.state = BoardState.CREATED;
        setupBoard(rows, columns);
    }

    public Board(String boardTitle, int rows, int columns, SystemUser owner) {
        this.boardTitle = new BoardTitle(boardTitle);
        this.num_rows = rows;
        this.num_columns = columns;
        this.owner = owner;
        this.state = BoardState.CREATED;
        setupBoard(rows, columns);
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

    public PostIt createPostIt(int cellId) {
        return new PostIt(cellId);
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
        return "\nBoard: " +
                "\nboardTitle: " + boardTitle.getBoardTitle() +
                "\nwith " + cells.size() + " cells";
    }
}
