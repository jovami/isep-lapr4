package eapli.base.board.dto;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import lombok.Getter;

import java.util.Objects;

public class BoardParticipantDTO {

    @Getter
    private final SystemUser user;

    @Getter
    private final Board board;

    @Getter
    private final boolean writePermissions;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardParticipantDTO that = (BoardParticipantDTO) o;
        return writePermissions == that.writePermissions && Objects.equals(user, that.user);
    }


    public BoardParticipantDTO(BoardParticipant boardParticipant) {
        this.board = boardParticipant.board();
        this.user = boardParticipant.participant();
        this.writePermissions = boardParticipant.hasWritePermissions();
    }

    @Override
    public String toString() {
        return "Board: " + board.getBoardTitle().title() +
                "\nUser: " + user.username()+
                "\nPermissions"+ (writePermissions?"write":"read")+"\n";
    }



}
