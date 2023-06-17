package eapli.base.board.domain;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

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
import javax.persistence.Transient;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;
import lombok.Getter;

@Entity
@Table(name = "BOARD")
public class Board implements AggregateRoot<BoardTitle> {
    @Id
    private BoardTitle boardTitle;
    @Transient
    public static int MAX_ROWS = 20;
    @Transient
    public static int MAX_COLUMNS = 10;
    @Column(nullable = false)
    private int numRows;
    @Column(nullable = false)
    private int numColumns;
    @OneToOne
    private SystemUser owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardState state;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<Cell> cells = Collections.synchronizedList(new ArrayList<>());

    @ElementCollection
    private final List<BoardColumn> boardColumnList = new ArrayList<>();

    @ElementCollection
    private final List<BoardRow> boardRowList = new ArrayList<>();


    protected Board() {
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public Board(BoardTitle boardTitle, int rows, int columns, SystemUser owner) {
        Preconditions.noneNull(boardTitle, rows, columns, owner);

        this.boardTitle = boardTitle;
        setDimension(rows, columns);
        this.owner = owner;
        this.state = BoardState.CREATED;
        setupBoard(rows, columns);
    }

    public void setDimension(int numRows, int numColumns) {
        if (numRows > MAX_ROWS || numColumns > MAX_COLUMNS || numRows < 1 || numColumns < 1) {
            throw new IllegalArgumentException("Board dimension is not valid");
        }
        this.numRows = numRows;
        this.numColumns = numColumns;
    }

    public static void setMax(int maxRows, int maxColumns) {
        MAX_ROWS = maxRows;
        MAX_COLUMNS = maxColumns;
    }
    public boolean addPostIt(int row, int column, PostIt postIt) {
        synchronized (this.cells) {
            var idx = (row - 1) * this.numColumns + (column - 1);
            if (idx < 0 || idx >= this.cells.size())
                return false; // TODO: report invalid index
        }

        var cell = this.getCell(row, column);
        return cell.addPostIt(this, postIt);
    }

    public boolean changePostItData(int row, int column, String newData) {
        synchronized (this.cells) {
            var idx = (row - 1) * this.numColumns + (column - 1);
            if (idx < 0 || idx >= this.cells.size())
                return false; // TODO: report invalid index
        }

        var cell = this.getCell(row, column);
        return cell.changePostItData(this, newData);
    }


    public boolean undoChangeOnPostIt(int row, int column) {
        synchronized (this.cells) {
            var idx = (row - 1) * this.numColumns + (column - 1);
            if (idx < 0 || idx >= this.cells.size())
                return false; // TODO: report invalid index
        }

        var cell = this.getCell(row, column);
        var postit = cell.getPostIt();

        return cell.addUndoToHistory(this, postit);
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

    public SystemUser getOwner() {
        return owner;
    }

    public BoardState getState() {
        return state;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public synchronized Cell getCell(int row, int col) {
        synchronized (this.cells) {
            return cells.get(((row - 1) * numColumns) + (col - 1));
        }
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

    public SystemUser boardOwner() {
        return owner;
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

    public void movePostIt(int rowFrom, int colFrom, int rowTo, int colTo) {
        // if newCellId has not a post it assigned

        Cell from = getCell(rowFrom, colFrom);
        Cell to = getCell(rowTo, colTo);

        synchronized (from) {
            synchronized (to) {
                PostIt tmp = from.getPostIt();
                from.removePostIt();
                to.addPostIt(this, tmp);
            }
        }

    }

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
    public boolean equals(Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public BoardTitle identity() {
        return boardTitle;
    }

    @Override
    public String toString() {
        return "Board: " +
                "\nTitle: " + boardTitle.title() +
                ", with " + getNumRows() + " Rows and " + getNumColumns() + " Columns";
    }

    public Deque<BoardHistory> getHistory() {
        List<BoardHistory> sortedList = new ArrayList<>();

        for (Cell cell : cells) {
            Deque<BoardHistory> cellHistory = cell.getHistory();
            sortedList.addAll(cellHistory);
        }

        sortedList.sort(Comparator.comparing(BoardHistory::getTime));

        return new ArrayDeque<>(sortedList);
    }

}
