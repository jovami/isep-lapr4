package eapli.base.board.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.*;

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

    public boolean updatePostIt(int row, int column, String data, SystemUser user) {
        synchronized (this.cells) {
            var idx = (row - 1) * this.numColumns + (column - 1);
            if (idx < 0 || idx >= this.cells.size())
                return false; // TODO: report invalid index
        }

        return this.getCell(row, column).updatePostIt(this, data, user);
    }

    public Optional<String> movePostIt(int rowFrom, int colFrom, int rowTo, int colTo, SystemUser user) {
        synchronized (this.cells) {
            var idxFrom = (rowFrom - 1) * this.numColumns + (colFrom - 1);
            var idxTo = (rowTo - 1) * this.numColumns + (colTo - 1);
            if (idxFrom < 0 || idxFrom >= this.cells.size() || idxTo < 0 || idxTo >= this.cells.size())
                return Optional.empty(); // TODO: report invalid index
        }

        synchronized (getCell(rowFrom, colFrom)) {
            synchronized (getCell(rowTo, colTo)) {
                return getCell(rowFrom, colFrom).movePostIt(this, getCell(rowTo, colTo), user);
            }
        }
    }


    public Optional<String> undoChangeOnPostIt(int row, int column, SystemUser user) {
        synchronized (this.cells) {
            var idx = (row - 1) * this.numColumns + (column - 1);
            if (idx < 0 || idx >= this.cells.size())
                return Optional.empty(); // TODO: report invalid index
        }

        return this.getCell(row, column).undoPostItChange(this, user);
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
        synchronized (this.cells) {
            return new ArrayList<>(this.cells);
        }
    }

    // TODO: needs bound checking
    public Cell getCell(int row, int col) {
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
        var sortedList = new LinkedList<BoardHistory>();
        synchronized (this.cells) {
            for (Cell cell : cells) {
                Deque<BoardHistory> cellHistory = cell.getHistory();
                sortedList.addAll(cellHistory);
            }
        }
        sortedList.sort(Comparator.comparing(BoardHistory::getTime).reversed());
        return (sortedList);
    }

}
