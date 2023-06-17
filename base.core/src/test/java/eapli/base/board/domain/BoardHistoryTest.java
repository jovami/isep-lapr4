package eapli.base.board.domain;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class BoardHistoryTest {

    @Test
    public void testConstructorWithValidDTO() {
        String str = "CREATE\tboard1\t1,1\t01-01-2023,12:00\tNew content";

        BoardHistory history = new CreatePostIt(str);

        assertEquals("CREATE", history.getType());
        assertEquals(BoardTitle.valueOf("board1"), history.getBoardTitle());
        assertEquals("1", history.getRow1());
        assertEquals("1", history.getColumn1());
        assertEquals(LocalDateTime.parse("01-01-2023,12:00", DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm")),
                history.getTime());
        assertNull(history.getPrevContent());
        assertEquals("New content", history.getPosContent());
    }
    @Test
    public void testUndo() {
        /*
        String str = "UNDO\tboard1\t1,1\t01-01-2023,12:00\tOld content\tNew content";

        BoardRepository repo = PersistenceContext.repositories().boards();
        var b = repo.ofIdentity(BoardTitle.valueOf("board1")).get();
        b.undoChangeOnPostIt(1,1);


        BoardHistory history = new UndoPostIt(str);

        assertEquals("UNDO", history.getType());
        assertEquals(BoardTitle.valueOf("board1"), history.getBoardTitle());
        assertEquals("1", history.getRow1());
        assertEquals("1", history.getColumn1());
        assertEquals(LocalDateTime.parse("01-01-2023,12:00", DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm")),
                history.getTime());
        assertEquals("New content", history.getPrevContent());
        assertEquals("Old content", history.getPosContent());*/
    }

    @Test
    public void testGetType() {
        BoardHistory history = new CreatePostIt();
        String type = history.getType();

        assertEquals("CREATE", type);
    }
}
