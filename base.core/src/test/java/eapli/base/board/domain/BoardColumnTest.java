package eapli.base.board.domain;

import org.junit.jupiter.api.Test;

import javax.persistence.Column;

import static org.junit.jupiter.api.Assertions.*;

class BoardColumnTest {

    @Test
    public void testGetColumnTitle() {
        BoardColumn column = new BoardColumn();
        column.setColumnTitle("Test Column");

        String columnTitle = column.getColumnTitle();

        assertEquals("Test Column", columnTitle);
    }

    @Test
    public void testSetColumnTitle() {
        BoardColumn column = new BoardColumn();

        column.setColumnTitle("New Column Title");

        assertEquals("New Column Title", column.getColumnTitle());
    }
}