package eapli.base.board.domain;

import eapli.base.board.domain.domain.*;
import eapli.base.course.domain.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private final String title = "test";
    private final int rows = 15;
    private final int columns = 10;
    private final int cellId = 10;
    private Board board = null;


    @BeforeEach
    void BeforeEach() {
        board = new Board(title, rows, columns);
    }

    @Test
    void ensureCellCreation() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int cellId = row * columns + column;
                BoardRow boardRow = new BoardRow(row);
                BoardColumn boardColumn = new BoardColumn(column);
                Cell cell = new Cell(cellId, boardRow, boardColumn);
                assertEquals(cell, board.getCells().get(cellId));
            }
        }
    }

    @Test
    void ensureRowIdsAreAdded() {
        for (int i = 0; i < rows; i++) {
            BoardRow boardRow = new BoardRow(i);
            assertEquals(boardRow, board.getBoardRowList().get(i));
        }
    }

    @Test
    void ensureColumnsIdsAreAdded() {
        for (int i = 0; i < columns; i++) {
            BoardColumn boardColumn = new BoardColumn(i);
            assertEquals(boardColumn, board.getBoardColumnList().get(i));
        }
    }

    @Test
    void ensurePostItCreation() {
        PostIt postIt = new PostIt(cellId);
        assertEquals(postIt, board.createPostIt(cellId));
    }

    @Test
    void ensurePostItCanAlterCell() {
        PostIt postIt = new PostIt(cellId);
        postIt.alterCell(cellId + 1);
        assertEquals(cellId + 1, postIt.getCellId());
    }

    @Test
    void ensurePostItCanBeMoved() {
        PostIt postIt = new PostIt(cellId);
        assertEquals(cellId, postIt.getCellId());

        assertNotEquals(cellId + 1, postIt.getCellId());
        postIt.alterCell(cellId + 1);

        //board.movePostIt(cellId+1,postIt);
        assertEquals(cellId + 1, postIt.getCellId());

    }


    @Test
    void ensureArchiveBoardState() {
        board.archiveBoard();
        assertEquals(BoardState.ARCHIVED, board.getState());
    }

    @Test
    void ensureShareBoardState() {
        board.sharedBoard();
        assertEquals(BoardState.SHARED, board.getState());
    }

    @Test
    void ensureCreateBoardState() {
        board.createdBoard();
        assertEquals(BoardState.CREATED, board.getState());
    }

    @Test
    void ensureSameAsVerify() {
        assertFalse(board.sameAs(new Object()));
    }

    @Test
    void ensureBoardSameName() {
        Board board2 = new Board("test", 10,5);
        assertTrue(board.sameAs(board2));
    }

    @Test
    void ensureIdentity() {
        BoardTitle boardTitle = board.getBoardTitle();
        assertEquals(title, boardTitle.getBoardTitle());
    }

    @Test
    void ensureToString() {
        String expected = "\nBoard: " +
                "\nboardTitle: " + title +
                "\nwith " + rows * columns + " cells";
        assertEquals(expected, board.toString());

    }
}