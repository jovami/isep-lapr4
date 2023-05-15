package eapli.base.board;

import eapli.base.board.domain.*;
import eapli.base.course.domain.CourseName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void ensureGetBoardColumnId() {
        BoardColumn boardColumn = board.getBoardColumnList().get(0);
        assertEquals( 0,boardColumn.getColumnId());
        boardColumn = board.getBoardColumnList().get(7);
        assertEquals( 7,boardColumn.getColumnId());

    }
    @Test
    void ensureGetBoardColumnTitle() {
        BoardColumn boardColumn = board.getBoardColumnList().get(0);

        assertNull(boardColumn.getColumnTitle());
    }
    @Test
    void ensureSetBoardColumnTitle() {
        BoardColumn boardColumn = board.getBoardColumnList().get(0);
        boardColumn.setColumnTitle("Processos");

        assertEquals("Processos", boardColumn.getColumnTitle());
    }

    @Test
    public void ensureColumnHashCode(){
        BoardColumn boardColumn1 = new BoardColumn(1);
        BoardColumn boardColumn2 = new BoardColumn(1);
        assertEquals(boardColumn1.hashCode(),boardColumn2.hashCode());
    }


    @Test
    void ensureGetBoardRowId() {
        BoardRow boardRow = board.getBoardRowList().get(0);
        assertEquals( 0,boardRow.getRowId());
        boardRow = board.getBoardRowList().get(7);
        assertEquals( 7,boardRow.getRowId());

    }
    @Test
    void ensureGetBoardRowTitle() {
        BoardRow boardRow = board.getBoardRowList().get(0);

        assertNull(boardRow.getRowTitle());
    }
    @Test
    void ensureSetBoardRowTitle() {
        BoardRow boardRow = board.getBoardRowList().get(0);
        boardRow.setRowTitle("Processos");

        assertEquals("Processos", boardRow.getRowTitle());
    }

    @Test
    public void ensureRowHashCode(){
        BoardRow boardRow1 = new BoardRow(4);
        BoardRow boardRow2 = new BoardRow(4);
        assertEquals(boardRow1.hashCode(),boardRow2.hashCode());
    }

    @Test
    void ensureGetBoardTitle() {
        BoardTitle boardTitle = board.getBoardTitle();

        assertEquals("test", boardTitle.getBoardTitle());
    }

    @Test
    void ensureSetBoardTitle() {
        BoardTitle boardTitle = board.getBoardTitle();
        boardTitle.setBoardTitle("titleOfBoard");

        assertEquals("titleOfBoard", boardTitle.getBoardTitle());
    }

    @Test
    public void ensureTitleHashCode(){
        BoardTitle boardTitle1 = new BoardTitle("hello");
        BoardTitle boardTitle2 = new BoardTitle("hello");

        assertEquals(boardTitle1.hashCode(),boardTitle2.hashCode());
    }

    @Test
    public void ensureCompareBoardTitle(){
        BoardTitle boardTitle1 = new BoardTitle("test");
        BoardTitle boardTitle2 = new BoardTitle("test2");
        int equal = 0;
        int different =1;

        assertEquals(equal,boardTitle1.compareTo(board.getBoardTitle()));
        assertEquals(different,boardTitle2.compareTo(board.getBoardTitle()));
    }

    @Test
    public void ensureGetCellId(){
        int cellId = 2;
        assertEquals(cellId, board.getCells().get(cellId).getCellId());
    }
    @Test
    public void ensureCellHashCode(){
        BoardRow boardRow = new BoardRow(0);
        BoardColumn boardColumn = new BoardColumn(2);
        Cell cell1 = new Cell(2,boardRow,boardColumn);
        Cell cell2 = new Cell(2,boardRow,boardColumn);

        assertEquals(cell1.hashCode(),cell2.hashCode());
    }

    @Test
    public void ensurePostItHashCode(){
        PostIt postIt1 = new PostIt(2);
        PostIt postIt2 = new PostIt(2);

        assertEquals(postIt1.hashCode(),postIt2.hashCode());
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