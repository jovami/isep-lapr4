package eapli.base.board.domain;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
import eapli.base.board.domain.BoardParticipantPermissions;
import eapli.base.board.domain.BoardTitle;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardParticipantTest {
    private final String title = "test";

    private SystemUser user;
    private final String username = "Dummy";
    private Board board;

    @Before
    public void BeforeEach() {
        int MAX_ROWS = 20;
        int MAX_COLUMNS = 10;
        int rows = 15;
        int columns = 10;
        Board.setMax(MAX_ROWS, MAX_COLUMNS);
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        user = userBuilder.with(username, "Password1", "Dummy", "Dummy", "a@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        board = new Board(BoardTitle.valueOf(title), rows, columns, user);

    }


    @Test
    public void participantWithReadPermissions() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.READ);
        assertFalse(p.hasWritePermissions());
        assertEquals(BoardParticipantPermissions.READ, p.permission());
    }
    @Test
    public void board() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.READ);
        assertEquals(board, p.board());
    }

    @Test
    public void participant() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.READ);
        assertEquals(participant, p.participant());
    }
    @Test
    public void participantWithWritePermissions() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        assertTrue(p.hasWritePermissions());
        assertEquals(BoardParticipantPermissions.WRITE, p.permission());
    }

    @Test
    public void sameAsUserBoard() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        BoardParticipant p2 = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        assertTrue(p.sameAs(p2));
    }

    @Test
    public void samesAsSelf() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        assertTrue(p.sameAs(p));
    }

    @Test
    public void notSameAsOtherObject() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        assertFalse(p.sameAs(new Object()));
    }

    @Test
    public void notSameAsDiffUser() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        SystemUser participant2 = userBuilder.with("Participant2", "Password1", "Dummy", "Dummy", "participant2@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p2 = new BoardParticipant(board, participant2, BoardParticipantPermissions.WRITE);
        assertFalse(p.sameAs(p2));
    }

    @Test
    public void notSameAsDiffBoard() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        Board board2 = new Board(BoardTitle.valueOf("otherBoard"), 5, 5, user);
        BoardParticipant p2 = new BoardParticipant(board2, participant, BoardParticipantPermissions.WRITE);
        assertFalse(p.sameAs(p2));
    }
    @Test

    public void notSameAsSameUserBoard() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();

        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        BoardParticipant p2 = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        assertTrue(p.sameAs(p2));
    }

    @Test
    public void compareToBigger() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        assertEquals(-1, p.compareTo(1));
    }

    @Test
    public void compareToLower() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        assertEquals(1, p.compareTo(-1));
    }

    @Test
    public void compareToSame() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        assertEquals(0, p.compareTo(0));
    }

    @Test
    public void identity() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        assertEquals(Integer.valueOf(0),p.identity());

    }

    @Test
    public void hasIdentity() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SystemUser participant = userBuilder.with("Participant", "Password1", "Dummy", "Dummy", "participan@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        BoardParticipant p = new BoardParticipant(board, participant, BoardParticipantPermissions.WRITE);
        assertTrue(p.hasIdentity(0));
    }
}