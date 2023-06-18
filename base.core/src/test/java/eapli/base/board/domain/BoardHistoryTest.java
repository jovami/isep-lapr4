package eapli.base.board.domain;


import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class BoardHistoryTest {




    @Test
    public void testCreateGetType() {
        BoardHistory history = new CreatePostIt();
        String type = history.getType();

        assertEquals("CREATE", type);
    }

    @Test
    public void testUndoGetType() {
        BoardHistory history = new UndoPostIt();
        String type = history.getType();

        assertEquals("UNDO", type);
    }

    @Test
    public void testRemoveGetType() {
        BoardHistory history = new RemovePostIt();
        String type = history.getType();

        assertEquals("REMOVE", type);
    }

    @Test
    public void testUpdateGetType() {
        BoardHistory history = new ChangePostIt();
        String type = history.getType();

        assertEquals("UPDATE", type);
    }




}
