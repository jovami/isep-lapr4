package eapli.base.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardColumn;
import eapli.base.board.domain.BoardRow;
import eapli.base.board.domain.BoardState;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.domain.Cell;
import eapli.base.board.domain.PostIt;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

public class BoardTest {
    private final String title = "test";
    private final int MAX_ROWS = 20;
    private final int MAX_COLUMNS = 10;
    private final int rows = 15;
    private final int columns = 10;
    private final int cellId = 10;
    private Board board = null;
    private SystemUser user;
    private final String username = "Tony";

    @Before
    public void BeforeEach() {
        Board.setMax(MAX_ROWS, MAX_COLUMNS);
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        user = userBuilder.with(username, "Password1", "dummy", "dummy", "a@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        board = new Board(BoardTitle.valueOf(title), rows, columns, user);

    }

    @Test
    public void ensureCellCreation() {
        int row = 2;
        int column = 1;
        BoardRow boardRow = new BoardRow(row);
        BoardColumn boardColumn = new BoardColumn(column);
        Cell cell = new Cell(boardRow, boardColumn);
        int cellId = row * columns + column;
        assertEquals(cell, board.getCells().get(cellId));
    }

    @Test
    public void ensureSetBoardRowId() {
        BoardRow boardRow = new BoardRow(2);
        boardRow.setRowId(5);
        assertEquals(5, boardRow.getRowId());
    }

    @Test
    public void ensureSetBoardColumnId() {
        BoardColumn boardColumn = new BoardColumn(2);
        boardColumn.setColumnId(5);
        assertEquals(5, boardColumn.getColumnId());
    }

    @Test
    public void ensureGetRow() {
        int row = 2;
        BoardRow boardRow = new BoardRow(row);
        int column = 2;
        BoardColumn boardColumn = new BoardColumn(column);
        Cell cell = new Cell(boardRow, boardColumn);
        assertEquals(boardRow, cell.getRow());
    }

    @Test
    public void ensureGetColumn() {
        int row = 2;
        BoardRow boardRow = new BoardRow(row);
        int column = 2;
        BoardColumn boardColumn = new BoardColumn(column);
        Cell cell = new Cell(boardRow, boardColumn);
        assertEquals(boardColumn, cell.getColumn());
    }

    @Test
    public void ensureRowIdsAreAdded() {
        for (int i = 0; i < rows; i++) {
            BoardRow boardRow = new BoardRow(i);
            assertEquals(boardRow, board.getBoardRowList().get(i));
        }
    }

    @Test
    public void ensureColumnsIdsAreAdded() {
        for (int i = 0; i < columns; i++) {
            BoardColumn boardColumn = new BoardColumn(i);
            assertEquals(boardColumn, board.getBoardColumnList().get(i));
        }
    }

    @Test
    public void ensurePostItCreation() {
        PostIt postIt = new PostIt(cellId);
        assertEquals(postIt, board.createPostIt(cellId));
    }

    @Test
    public void ensurePostItCanAlterCell() {
        PostIt postIt = new PostIt(cellId);
        postIt.alterCell(cellId + 1);
        assertEquals(cellId + 1, postIt.getCellId());
    }

    @Test
    public void ensurePostItCanBeMoved() {
        PostIt postIt = new PostIt(cellId);
        assertEquals(cellId, postIt.getCellId());

        assertNotEquals(cellId + 1, postIt.getCellId());
        postIt.alterCell(cellId + 1);

        // board.movePostIt(cellId+1,postIt);
        assertEquals(cellId + 1, postIt.getCellId());

    }

    @Test
    public void ensureGetBoardColumnId() {
        BoardColumn boardColumn = board.getBoardColumnList().get(0);
        assertEquals(0, boardColumn.getColumnId());
        boardColumn = board.getBoardColumnList().get(7);
        assertEquals(7, boardColumn.getColumnId());

    }

    @Test
    public void ensureGetBoardColumnTitle() {
        BoardColumn boardColumn = board.getBoardColumnList().get(0);

        assertNull(boardColumn.getColumnTitle());
    }

    @Test
    public void ensureSetBoardColumnTitle() {
        BoardColumn boardColumn = board.getBoardColumnList().get(0);
        boardColumn.setColumnTitle("Processos");

        assertEquals("Processos", boardColumn.getColumnTitle());
    }

    @Test
    public void ensureColumnHashCode() {
        BoardColumn boardColumn1 = new BoardColumn(1);
        BoardColumn boardColumn2 = new BoardColumn(1);
        assertEquals(boardColumn1.hashCode(), boardColumn2.hashCode());
    }

    @Test
    public void ensureGetBoardRowId() {
        BoardRow boardRow = board.getBoardRowList().get(0);
        assertEquals(0, boardRow.getRowId());
        boardRow = board.getBoardRowList().get(7);
        assertEquals(7, boardRow.getRowId());

    }

    @Test
    public void ensureGetBoardRowTitle() {
        BoardRow boardRow = board.getBoardRowList().get(0);

        assertNull(boardRow.getRowTitle());
    }

    @Test
    public void ensureSetBoardRowTitle() {
        BoardRow boardRow = board.getBoardRowList().get(0);
        boardRow.setRowTitle("Processos");

        assertEquals("Processos", boardRow.getRowTitle());
    }

    @Test
    public void ensureRowHashCode() {
        BoardRow boardRow1 = new BoardRow(4);
        BoardRow boardRow2 = new BoardRow(4);
        assertEquals(boardRow1.hashCode(), boardRow2.hashCode());
    }

    @Test
    public void ensureGetBoardTitle() {
        BoardTitle boardTitle = board.getBoardTitle();

        assertEquals("test", boardTitle.title());
    }

    @Test
    public void ensureTitleHashCode() {
        BoardTitle boardTitle1 = BoardTitle.valueOf("hello");
        BoardTitle boardTitle2 = BoardTitle.valueOf("hello");

        assertEquals(boardTitle1.hashCode(), boardTitle2.hashCode());
    }

    @Test
    public void ensureCompareBoardTitle() {
        BoardTitle boardTitle1 = BoardTitle.valueOf("test");
        BoardTitle boardTitle2 = BoardTitle.valueOf("test2");
        int equal = 0;
        int different = 1;

        assertEquals(equal, boardTitle1.compareTo(board.getBoardTitle()));
        assertEquals(different, boardTitle2.compareTo(board.getBoardTitle()));
    }

    @Test
    public void ensureCellHashCode() {
        BoardRow boardRow = new BoardRow(0);
        BoardColumn boardColumn = new BoardColumn(2);
        Cell cell1 = new Cell(boardRow, boardColumn);
        Cell cell2 = new Cell(boardRow, boardColumn);

        assertEquals(cell1.hashCode(), cell2.hashCode());
    }

    @Test
    public void ensurePostItHashCode() {
        PostIt postIt1 = new PostIt(2);
        PostIt postIt2 = new PostIt(2);

        assertEquals(postIt1.hashCode(), postIt2.hashCode());
    }

    @Test
    public void ensureArchiveBoardState() {
        board.archiveBoard();
        assertEquals(BoardState.ARCHIVED, board.getState());
    }

    @Test
    public void ensureShareBoardState() {
        board.sharedBoard();
        assertEquals(BoardState.SHARED, board.getState());
    }

    @Test
    public void ensureCreateBoardState() {
        board.createdBoard();
        assertEquals(BoardState.CREATED, board.getState());
    }

    @Test
    public void ensureSameAsVerify() {
        assertFalse(board.sameAs(new Object()));
    }

    @Test
    public void ensureBoardSameName() {
        Board board2 = new Board(BoardTitle.valueOf("test"), 10, 5, user);
        assertTrue(board.sameAs(board2));
    }

    @Test
    public void ensureIdentity() {
        BoardTitle boardTitle = board.getBoardTitle();
        assertEquals(title, boardTitle.title());
    }

    @Test
    public void ensureToString() {
        String expected = " Board: " +
                "\nTitle: " + title +
                ", with " + rows * columns + " Cells";
        assertEquals(expected, board.toString());

    }
}
