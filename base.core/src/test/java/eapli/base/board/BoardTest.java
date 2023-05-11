package eapli.base.board;

import eapli.base.board.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private final String title = "test";
    private final int rows = 10;
    private final int columns = 15;
    private Board board = null;


    @BeforeEach
    void BeforeEach() {
        board = new Board(title, rows, columns);
    }

    @Test
    void createCells() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int cellId = row * columns + column;
                BoardRow boardRow = new BoardRow(row);
                BoardColumn boardColumn = new BoardColumn(column);
                Cell cell = new Cell(cellId, boardRow, boardColumn);
                assertEquals(cell.getCellId(), board.getCells().get(cellId).getCellId());
            }
        }
    }

    @Test
    void addRowIds() {
        for (int i = 0; i < rows; i++) {
            BoardRow boardRow = new BoardRow(i);
            assertEquals(boardRow.getRowId(), board.getBoardRowList().get(i).getRowId());
        }
    }

    @Test
    void addColumnIds() {
        for (int i = 0; i < columns; i++) {
            BoardColumn boardColumn = new BoardColumn(i);
            assertEquals(boardColumn.getColumnId(), board.getBoardColumnList().get(i).getColumnId());
        }
    }

    @Test
    void createPostIt() {
        int cellId = 10;
        PostIt postIt = new PostIt(cellId);
        assertEquals(postIt.getCellId(), board.createPostIt(cellId).getCellId());
    }

    @Test
    void alterCell() {
        int cellId = 10;
        PostIt postIt = new PostIt(cellId);
        postIt.alterCell(cellId+1);
        assertEquals(cellId+1, postIt.getCellId());
    }

    @Test
    void movePostIt() {
        int cellId = 10;
        PostIt postIt = new PostIt(cellId);
        assertEquals(cellId,postIt.getCellId());

        assertNotEquals(cellId+1,postIt.getCellId());
        postIt.alterCell(cellId+1);

        //board.movePostIt(cellId+1,postIt);
        assertEquals(cellId+1,postIt.getCellId());

    }


    @Test
    void archiveBoard() {
        board.archiveBoard();
        assertEquals(BoardState.ARCHIVED, board.getState());
    }

    @Test
    void sharedBoard() {
        board.sharedBoard();
        assertEquals(BoardState.SHARED, board.getState());
    }

    @Test
    void createdBoard() {
        board.createdBoard();
        assertEquals(BoardState.CREATED, board.getState());
    }
}