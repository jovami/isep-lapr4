package eapli.base.board.dto;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;

import java.util.Objects;

public class BoardParticipantDTO {
    private final BoardParticipant boardParticipant;

    public BoardParticipantDTO(BoardParticipant boardParticipant)
    {
        this.boardParticipant = boardParticipant;
    }

    public BoardParticipant boardParticipant(){return this.boardParticipant;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || obj.getClass() != this.getClass())
            return false;
        var o = (Board) obj;
        return this.boardParticipant.sameAs(((BoardParticipantDTO) obj).boardParticipant) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.boardParticipant);
    }

    @Override
    public String toString() {
        return " "  + boardParticipant;
    }
}
