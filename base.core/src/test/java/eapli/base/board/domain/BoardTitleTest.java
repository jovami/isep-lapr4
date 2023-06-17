package eapli.base.board.domain;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTitleTest {
    @Test
    public void testTitle() {
        String title = "My Board Title";
        BoardTitle boardTitle = BoardTitle.valueOf(title);
        String actualTitle = boardTitle.title();

        assertEquals(title, actualTitle);
    }

    @Test
    public void testEqualsAndHashCode() {
        BoardTitle boardTitle1 = BoardTitle.valueOf("Title");
        BoardTitle boardTitle2 = BoardTitle.valueOf("Title");
        BoardTitle boardTitle3 = BoardTitle.valueOf("Different Title");

        assertEquals(boardTitle1, boardTitle2);
        assertEquals(boardTitle1.hashCode(), boardTitle2.hashCode());
        assertNotEquals(boardTitle1, boardTitle3);
        assertNotEquals(boardTitle1.hashCode(), boardTitle3.hashCode());
    }

    @Test
    public void testCompareTo() {
        BoardTitle boardTitle1 = BoardTitle.valueOf("AAA");
        BoardTitle boardTitle2 = BoardTitle.valueOf("BBB");
        BoardTitle boardTitle3 = BoardTitle.valueOf("AAA");

        int compareResult1 = boardTitle1.compareTo(boardTitle2);
        int compareResult2 = boardTitle1.compareTo(boardTitle3);

        assertEquals(-1,compareResult1);
        assertEquals(0, compareResult2);
    }
    @Test
    public void testEquals_SameObject() {
        BoardTitle title1 = new BoardTitle("Title");

        assertTrue(title1.equals(title1));
    }

    @Test
    public void testEquals_NullObject() {
        BoardTitle title1 = new BoardTitle("Title");

        assertNotEquals(null, title1);
    }

    @Test
    public void testEquals_DifferentClass() {
        BoardTitle title1 = new BoardTitle("Title");
        String title2 = "Title";

        assertNotEquals(title1, title2);
    }

    @Test
    public void testEquals_EqualObjects() {
        BoardTitle title1 = new BoardTitle("Title");
        BoardTitle title2 = new BoardTitle("Title");

        Assert.assertEquals(title1, title2);
        Assert.assertEquals(title2, title1);
    }

    @Test
    public void testEquals_UnequalObjects() {
        BoardTitle title1 = new BoardTitle("Title1");
        BoardTitle title2 = new BoardTitle("Title2");

        assertNotEquals(title1, title2);
        assertNotEquals(title2, title1);
    }
}
