package eapli.base.board.domain;

import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    private Board board;
    private BoardRow row;
    private BoardColumn column;
    private PostIt postIt;
    private Cell cell;
    private SystemUser user1;


    @Before
    public void setup() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        user1 = userBuilder.with("tony", "Password1", "Alexandre", "Moreira", "alexmoreira@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        board = new Board(BoardTitle.valueOf("MyBoard"), 10, 10, user1);
        row = new BoardRow(1);
        column = new BoardColumn(1);
        postIt = new PostIt(user1, "PostItData");
        cell = new Cell(row, column);
    }

    @Test
    public void testAddPostIt() {
        boolean added = cell.addPostIt(board, postIt);

        assertTrue(added);
        assertTrue(cell.hasPostIt());
        assertEquals(postIt, cell.getPostIt());
    }

    @Test
    public void testAddPostIt_AlreadyHasPostIt() {
        cell.addPostIt(board, postIt);

        assertFalse(cell.addPostIt(board, new PostIt(user1, "NewPostItData")));
        assertTrue(cell.hasPostIt());
        assertEquals(postIt, cell.getPostIt());
    }

    @Test
    public void testChangePostItData() {
        // Arrange
        cell.addPostIt(board, postIt);

        // Act
        boolean changed = cell.updatePostIt(board, "NewPostItData", user1);

        // Assert
        assertTrue(changed);
        assertEquals("NewPostItData", postIt.getData());
    }

    @Test
    public void testChangePostItData_NoPostIt() {
        // Act
        boolean changed = cell.updatePostIt(board, "NewPostItData", user1);

        // Assert
        assertFalse(changed);
    }

    @Test
    public void testRemovePostIt() {
        // Arrange
        cell.addPostIt(board, postIt);

        // Act
        boolean removed = cell.removePostIt(board);

        // Assert
        assertTrue(removed);
        assertFalse(cell.hasPostIt());
        assertNull(cell.getPostIt());
    }

    @Test
    public void testRemovePostIt_NoPostIt() {
        // Act
        boolean removed = cell.removePostIt(board);

        // Assert
        assertFalse(removed);
    }

    @Test
    public void testMovePostIt() {
        // Arrange
        Cell destinationCell = new Cell(new BoardRow(2), new BoardColumn(2));
        cell.addPostIt(board, postIt);

        // Act
        var moved = cell.movePostIt(board, destinationCell, user1);

        // Assert
        assertEquals(destinationCell.getPostIt().getData() , moved.orElseThrow());
        assertFalse(cell.hasPostIt());
        assertTrue(destinationCell.hasPostIt());
        assertEquals(postIt, destinationCell.getPostIt());
    }

    @Test
    public void testMovePostIt_NoPostIt() {
        // Arrange
        Cell destinationCell = new Cell(new BoardRow(2), new BoardColumn(2));

        // Act
        var moved = cell.movePostIt(board, destinationCell, user1);

        // Assert
        assertFalse(moved.isPresent());
        assertFalse(cell.hasPostIt());
        assertFalse(destinationCell.hasPostIt());
    }

    @Test
    public void testMovePostIt_DestinationCellHasPostIt() {
        // Arrange
        Cell destinationCell = new Cell(new BoardRow(2), new BoardColumn(2));
        cell.addPostIt(board, postIt);
        destinationCell.addPostIt(board, new PostIt(user1, "OtherPostItData"));

        // Act
        var moved = cell.movePostIt(board, destinationCell, user1);

        // Assert
        assertFalse(moved.isPresent());
        assertTrue(cell.hasPostIt());
        assertEquals(postIt, cell.getPostIt());
        assertTrue(destinationCell.hasPostIt());
        assertNotEquals(postIt, destinationCell.getPostIt());
    }





}